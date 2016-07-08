/**
 * Copyright © 2016 Andrea Parise
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.java.eju.arg.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Andrea Parise
 * @version 0.1
 */

public interface ArgsUtility {

    /**
     * Check the arguments and bind them with the params signed as @arg
     *
     * @param cl Class to fetch
     * @param args Application arguments to bind with class parameters
     */
    static void parseArgs(Class cl, String[] args) {

        try {

            String hyphen = "-";

            Map<String, Field> annotations = new LinkedHashMap<>();
            Map<String, String> nameToKey = new HashMap<>();

            int nRequired = 0;

            // Retrieve static fields of class signed as @Arg

            for (Field field : cl.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) continue;
                Arg annotation = field.getAnnotation(Arg.class);
                if (annotation == null) continue;
                field.setAccessible(true);
                nRequired += annotation.required() ? 1 : 0;
                String key = hyphen + annotation.key();
                String longName = hyphen + hyphen + field.getName();
                annotations.put(key, field);
                nameToKey.put(longName, key);
            }

            // Check args

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (annotations.containsKey(arg) || (arg = nameToKey.get(arg)) != null) {
                    Field field = annotations.get(arg);
                    Arg annotation = field.getAnnotation(Arg.class);
                    nRequired -= annotation.required() ? 1 : 0;
                    Class type =  field.getType();
                    if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
                        field.set(null, !(Boolean) field.get(null));
                    } else {
                        String value = args[++i];
                        if (type.isAssignableFrom(String.class))
                            field.set(null, value);
                        else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class))
                            field.set(null, Integer.parseInt(value));
                        else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class))
                            field.set(null, Double.parseDouble(value));
                        else if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class))
                            field.set(null, Long.parseLong(value));
                        else if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class))
                            field.set(null, Float.parseFloat(value));
                        else throw new IllegalArgumentException();
                    }
                } else throw new IllegalArgumentException();
            }

            if (nRequired > 0) throw new IllegalArgumentException();

        } catch (Exception e) {
            printInfo(cl);
            printUsage(cl);
            System.exit(-1);
        }

    }

    /**
     * Print to console the application usage
     * @param cl Class to fetch
     */
    static void printUsage(Class cl) {

        String hyphen = "-";
        String endLine = "\n";
        String space = " ";
        String val = "<value>";
        int max = 0;

        Map<String, Field> fieldMap = new LinkedHashMap<>();

        // Retrieve static fields of class signed as @Arg

        for (Field field : cl.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            Arg annotation = field.getAnnotation(Arg.class);
            if (annotation == null) continue;
            String key = hyphen + annotation.key();
            fieldMap.put(key, field);
            int len = field.getName().length() + annotation.key().length();
            max = max > len ? max : len;
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(endLine);

        // Add command

        Application app = (Application) cl.getAnnotation(Application.class);
        stringBuilder.append("Usage: java -jar " + (app.name().equals("") ? cl.getSimpleName() : app.name()) + ".jar");

        // Add required fields

        for (String arg : fieldMap.keySet()) {
            Field value = fieldMap.get(arg);
            Arg annotation = value.getAnnotation(Arg.class);
            if (annotation.required())
                if (!value.getType().isAssignableFrom(Boolean.class))
                    stringBuilder.append(space + arg + space + val);
                else
                    stringBuilder.append(space + arg);
        }

        // Add optional fields

        if (!fieldMap.isEmpty())
            stringBuilder.append(space + "[-options]" + endLine + "options:" + endLine);

        for (String arg : fieldMap.keySet()) {
            Field value = fieldMap.get(arg);

            Arg annotation = value.getAnnotation(Arg.class);

            stringBuilder.append(arg);

            if (value.getName() != null)
                stringBuilder.append( space + "or --" + value.getName());

            int nSpace = 0;

            if (!value.getType().isAssignableFrom(Boolean.class))
                stringBuilder.append(space + val + space);
            else
                nSpace = val.length() + 2;

            nSpace += max - (value.getName().length() + annotation.key().length());
            for (int i = 0; i <= nSpace + 1; i++) {
                stringBuilder.append(space);
            }

            stringBuilder.append(annotation.description());

            stringBuilder.append(endLine);

        }

        System.out.println(stringBuilder.toString());

    }

    /**
     * Print to console the application info
     * @param cl Class to fetch
     */
    static void printInfo(Class cl) {

        String endLine = "\n";
        String empty = "";
        String space = " ";

        Application app = (Application) cl.getAnnotation(Application.class);

        if (app != null) {

            StringBuilder info = new StringBuilder();

            info.append(endLine);

            info.append(app.name().isEmpty() ? cl.getSimpleName() : app.name());

            info.append(endLine);

            info.append(app.version().isEmpty() ? empty : "Version" + space + app.version() + endLine);

            info.append(app.author().isEmpty() ? empty : "By" + space + app.author() + endLine);

            info.append(app.copyright().isEmpty() ? empty : app.copyright() + endLine);

            info.append(endLine);

            info.append(app.description().isEmpty() ? empty : "Description:" + space + app.description() + endLine);

            System.out.print(info.toString());

        }

    }

}
