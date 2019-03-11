package com.zmj.mvp.testsocket.utils;

import java.security.Provider;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/3/11
 * Description :解决Android7.0 AES加密问题
 */
public class CryptoProvider extends Provider {

    private static CryptoProvider cryptoProvider;
    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     */
    private CryptoProvider() {
        /**
         * @param name    the provider name.
         * @param version the provider version number.
         * @param info    a description of the provider and its services.
         */
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }

    public static CryptoProvider getCryptoProvider() {
        if (cryptoProvider == null){
            synchronized (CryptoProvider.class){
                if (cryptoProvider == null){
                    cryptoProvider = new CryptoProvider();
                }
            }
        }
        return cryptoProvider;
    }
}
