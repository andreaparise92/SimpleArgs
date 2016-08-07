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
 * This annotation used on class define the application:
 *<p>
 *      - name;
 *<p>
 *      - description;
 *<p>
 *      - version;
 *<p>
 *      - author;
 *<p>
 *      - copyright;
 *
 * @author      Andrea Parise
 * @version     0.1
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Application {

    /**
     *
     * Define the application name
     *
     * @return Application name
     */
    String name() default "";

    /**
     *
     * Define the application author
     *
     * @return Application author
     */
    String author() default "";

    /**
     *
     * Define the application description
     *
     * @return Application description
     */
    String description() default "";

    /**
     *
     * Define the application copyright
     *
     * @return Application copyright
     */
    String copyright() default "";

    /**
     *
     * Define the application version
     *
     * @return Application version
     */
    String version() default "";

}
