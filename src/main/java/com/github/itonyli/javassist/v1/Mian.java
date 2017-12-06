package com.github.itonyli.javassist.v1;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class Mian {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ccb = pool.get("com.github.itonyli.javassist.v1.B");
        CtClass cca = pool.get("com.github.itonyli.javassist.v1.A");

        CtClass ccc = pool.makeClass("com.github.itonyli.javassist.v1.C");
        CtField ageField = new CtField(CtClass.intType, "age", ccc);
        ccc.addField(ageField);
        CtMethod getMethod = CtNewMethod.make("public int getAge() { return this.age;}", ccc);
        CtMethod setMethod = CtNewMethod.make("public void setAge(int age) { this.age = age;}", ccc);
        ccc.addMethod(getMethod);
        ccc.addMethod(setMethod);

        ccc.writeFile("/Users/tony/Tmp");

        ccb.setSuperclass(ccc);
        cca.setSuperclass(ccb);


        Method[] methods = ccc.toClass().getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }


        //pool.insertClassPath("")


    }
}
