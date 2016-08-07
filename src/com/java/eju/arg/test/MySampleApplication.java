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

package com.java.eju.arg.test;

import com.java.eju.arg.annotation.Arg;
import com.java.eju.arg.annotation.ArgsUtility;
import com.java.eju.arg.annotation.Application;

/**
 *
 * Sample application for testing purpose
 *
 * @author      Andrea Parise
 * @version     0.1
 */

@Application(
        version = "0.1",
        description = "Sample application for test.",
        author = "Andrea Parise",
        copyright = "Copyright © 2016 Andrea Parise"
)
public class MySampleApplication{

    @Arg(key = "u",description = "Your username", required = true)
    private static String username;

    @Arg(key = "p",description = "Your password (>7)", required = true, check = "checkPassword")
    private static String password;

    @Arg(key = "n",description = "Your name")
    private static String name = "No name";

    @Arg(key = "s",description = "False to run silent")
    private static Boolean print = true;

    private static boolean checkPassword(String password){
        return password.length()>8;
    }

    public static void print() {
        String out =  "Your bind parameters:{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", print=" + print +
                '}';
        System.out.println(out);
    }

    public static void main (String [] args){

        ArgsUtility.parseArgs(MySampleApplication.class, args);

        if(print) print();

    }

}
