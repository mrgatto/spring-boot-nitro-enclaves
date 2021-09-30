package com.github.mrgatto.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.github.mrgatto.configuration.HostConfiguration;
import com.github.mrgatto.configuration.JacksonConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({JacksonConfiguration.class, HostConfiguration.class})
public @interface EnableNitroEnclavesHostSide {

}
