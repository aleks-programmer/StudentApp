package com.java.developer.challenge.StudentApp.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class IDOrGenerate extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (obj == null) throw new HibernateException(new NullPointerException()) ;

        if ((((Student) obj).getId()) == null) {
            Serializable id = super.generate(session, obj) ;
            return id;
        } else {
            return ((Student) obj).getId();

        }
    }
}
