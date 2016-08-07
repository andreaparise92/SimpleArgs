/**

 Copyright © 2016 Andrea Parise

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package com.java.eju.arg.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * This annotation used on class parameter sign it as an argument of application.
 * <p>
 * A parameter signed as @arg can be passed through console arguments whit signature:
 * <p>
 * "-key value" or "--name value"
 * <p>
 *
 * @author      Andrea Parise
 * @version     0.1
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Arg {

    /**
     *
     * Define the argument key
     *
     * @return Argument key
     */
    String key();

    /**
     *
     * Define the argument description
     *
     * @return Argument description
     */
    String description();

    /**
     * Define if argument the argument is required or optional
     *
     * @return True if argument is required, false otherwise
     */
    boolean required() default false;

    /**
     *
     * Method to check if input is valid
     *
     * @return Name of checker method
     */
    String check() default "";

}
