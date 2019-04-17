package com.sucl.sbjms.security.auth;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.MutablePrincipalCollection;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sucl
 * @date 2019/4/15
 */
@Data
public abstract class AbstractGenericAccount implements GenericAccount{
    private IUser user;

    private PrincipalCollection principals;
    private Object credentials;
    private ByteSource credentialsSalt;

    protected Set<String> roles;
    protected Set<String> stringPermissions;
    protected Set<Permission> objectPermissions;

    public AbstractGenericAccount(IUser principal,Object credentials){
        this.principals = new SimplePrincipalCollection(principal, "");
        this.credentials = credentials;
        this.user = principal;
    }

    public AbstractGenericAccount(IUser principal,Object credentials, String realmName){
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = credentials;
        this.user = principal;
    }

    public AbstractGenericAccount(IUser principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName){
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
        this.user = principal;
    }

    @Override
    public void merge(AuthenticationInfo info) {
        if (info == null || info.getPrincipals() == null || info.getPrincipals().isEmpty()) {
            return;
        }

        if (this.principals == null) {
            this.principals = info.getPrincipals();
        } else {
            if (!(this.principals instanceof MutablePrincipalCollection)) {
                this.principals = new SimplePrincipalCollection(this.principals);
            }
            ((MutablePrincipalCollection) this.principals).addAll(info.getPrincipals());
        }
        if (this.credentialsSalt == null && info instanceof SaltedAuthenticationInfo) {
            this.credentialsSalt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
        }

        Object thisCredentials = getCredentials();
        Object otherCredentials = info.getCredentials();

        if (otherCredentials == null) {
            return;
        }

        if (thisCredentials == null) {
            this.credentials = otherCredentials;
            return;
        }

        if (!(thisCredentials instanceof Collection)) {
            Set newSet = new HashSet();
            newSet.add(thisCredentials);
            setCredentials(newSet);
        }

        // At this point, the credentials should be a collection
        Collection credentialCollection = (Collection) getCredentials();
        if (otherCredentials instanceof Collection) {
            credentialCollection.addAll((Collection) otherCredentials);
        } else {
            credentialCollection.add(otherCredentials);
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractGenericAccount)) return false;

        AbstractGenericAccount that = (AbstractGenericAccount) o;

        if (principals != null ? !principals.equals(that.principals) : that.principals != null) return false;

        return true;
    }

    @Override
    public Set<String> getRoles() {
        return user.getRoleIds();
    }

    @Override
    public Set<String> getStringPermissions() {
        return user.getStringPermissions();
    }

    @Override
    public Set<Permission> getObjectPermissions() {
        return user.getObjectPermissions();
    }
}
