package com.smattme.springboot.dbcolumnencryption.config;

import com.smattme.springboot.dbcolumnencryption.helpers.CryptoHelper;
import com.smattme.springboot.dbcolumnencryption.helpers.SpringContext;
import jakarta.persistence.AttributeConverter;


public class HmacSecretKeyAttributeConverter
        implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        CryptoHelper cryptoHelper = SpringContext.getBean(CryptoHelper.class);
        return cryptoHelper.encryptDeviceCredential(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        CryptoHelper cryptoHelper = SpringContext.getBean(CryptoHelper.class);
        return cryptoHelper.decryptDeviceCredential(dbData);
    }

}
