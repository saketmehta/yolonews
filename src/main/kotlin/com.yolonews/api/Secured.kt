package yolonews.api

import javax.ws.rs.NameBinding

/**
 * @author saket.mehta
 */
@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Secured