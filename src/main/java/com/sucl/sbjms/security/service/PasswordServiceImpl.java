package com.sucl.sbjms.security.service;

import com.sucl.sbjms.security.Constant;
import com.sucl.sbjms.security.config.ShiroProperties;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.crypto.hash.format.*;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ShiroProperties.class)
public class PasswordServiceImpl implements HashingPasswordService {

    @Autowired
    private ShiroProperties shiroProperties;

    public static final String DEFAULT_HASH_ALGORITHM = Constant.DEFAULT_HASH_ALGORITHM;
    public static final int DEFAULT_HASH_ITERATIONS = Constant.DEFAULT_HASH_ITERATIONS;

    private HashService hashService;
    private HashFormat hashFormat;
    private HashFormatFactory hashFormatFactory;

    private volatile boolean hashFormatWarned; //used to avoid excessive log noise

    public PasswordServiceImpl() {
        this.hashFormatWarned = false;

        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName(DEFAULT_HASH_ALGORITHM);
        hashService.setHashIterations(DEFAULT_HASH_ITERATIONS);
        hashService.setGeneratePublicSalt(true); //always want generated salts for user passwords to be most secure
        this.hashService = hashService;
        this.hashFormat = new Shiro1CryptFormat();
        this.hashFormatFactory = new DefaultHashFormatFactory();
    }

    public String encryptPassword(Object plaintext) {
        SimpleHash hash = new SimpleHash(DEFAULT_HASH_ALGORITHM, plaintext, null, DEFAULT_HASH_ITERATIONS);
        Hash hash2 = hashPassword(plaintext);
        return hash.toString();

//        Hash hash = hashPassword(plaintext);
//        checkHashFormatDurability();
//        return this.hashFormat.format(hash);
    }

    public Hash hashPassword(Object plaintext) {
        ByteSource plaintextBytes = createByteSource(plaintext);
        if (plaintextBytes == null || plaintextBytes.isEmpty()) {
            return null;
        }
        HashRequest request = createHashRequest(plaintextBytes);
        return hashService.computeHash(request);
    }

    public boolean passwordsMatch(Object plaintext, Hash saved) {
        ByteSource plaintextBytes = createByteSource(plaintext);

        if (saved == null || saved.isEmpty()) {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        } else {
            if (plaintextBytes == null || plaintextBytes.isEmpty()) {
                return false;
            }
        }
        HashRequest request = buildHashRequest(plaintextBytes, saved);
        Hash computed = this.hashService.computeHash(request);
        return saved.equals(computed);
    }

    protected void checkHashFormatDurability() {
        if (!this.hashFormatWarned) {
            HashFormat format = this.hashFormat;
            if (!(format instanceof ParsableHashFormat)) {
                this.hashFormatWarned = true;
            }
        }
    }

    protected HashRequest createHashRequest(ByteSource plaintext) {
        return new HashRequest.Builder().setSource(plaintext).build();
    }

    protected ByteSource createByteSource(Object o) {
        return ByteSource.Util.bytes(o);
    }

    public boolean passwordsMatch(Object submittedPlaintext, String saved) {
        ByteSource plaintextBytes = createByteSource(submittedPlaintext);
        if (saved == null || saved.length() == 0) {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        } else {
            if (plaintextBytes == null || plaintextBytes.isEmpty()) {
                return false;
            }
        }
        HashFormat discoveredFormat = this.hashFormatFactory.getInstance(saved);

        if (discoveredFormat != null && discoveredFormat instanceof ParsableHashFormat) {
            ParsableHashFormat parsableHashFormat = (ParsableHashFormat)discoveredFormat;
            Hash savedHash = parsableHashFormat.parse(saved);
            return passwordsMatch(submittedPlaintext, savedHash);
        }

        HashRequest request = createHashRequest(plaintextBytes);
        Hash computed = this.hashService.computeHash(request);
        String formatted = this.hashFormat.format(computed);

        return saved.equals(formatted);
    }

    protected HashRequest buildHashRequest(ByteSource plaintext, Hash saved) {
        //keep everything from the saved hash except for the source:
        return new HashRequest.Builder().setSource(plaintext)
                //now use the existing saved data:
                .setAlgorithmName(saved.getAlgorithmName())
                .setSalt(saved.getSalt())
                .setIterations(saved.getIterations())
                .build();
    }

    public HashService getHashService() {
        return hashService;
    }

    public void setHashService(HashService hashService) {
        this.hashService = hashService;
    }

    public HashFormat getHashFormat() {
        return hashFormat;
    }

    public void setHashFormat(HashFormat hashFormat) {
        this.hashFormat = hashFormat;
    }

    public HashFormatFactory getHashFormatFactory() {
        return hashFormatFactory;
    }

    public void setHashFormatFactory(HashFormatFactory hashFormatFactory) {
        this.hashFormatFactory = hashFormatFactory;
    }
}
