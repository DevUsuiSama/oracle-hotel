package com.hotel.challenge.decorators;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import javax.swing.JOptionPane;

import com.hotel.challenge.annotations.Edad;
import com.hotel.challenge.annotations.Telefono;
import com.hotel.challenge.utils.ValidarUtil;

public class AnnotationDecorator {

    private Object controller;
    private Object model;

    private record InnerAnnotationReading(Method method, Annotation annotation, Class<?> parameter) {
    }

    public AnnotationDecorator(Object controller, Object model) {
        this.controller = controller;
        this.model = model;
    }

    private boolean readController(String method, Function<InnerAnnotationReading, Boolean> lambda) {
        Method _method = null;
        try {
            _method = controller.getClass().getMethod(method, model.getClass());
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        var annotations = _method.getParameterAnnotations();

        boolean status = true;

        for (int j = 0; j < annotations.length; j++) {
            for (int j2 = 0; j2 < annotations[j].length; j2++) {
                var parameters = _method.getParameterTypes();
                for (int k = 0; k < parameters.length; k++) {
                    status = lambda.apply(new InnerAnnotationReading(
                            _method,
                            annotations[j][j2],
                            parameters[k]));
                    if (status)
                        break;
                }
            }
        }
        return status;
    }

    public Object methodExecute(String method) {
        boolean resultado = readController(method, (InnerAnnotationReading innerAnnotationReading) -> {
            boolean status = true;
            if (innerAnnotationReading.annotation().annotationType().getName().matches("(.*)Validar")) {
                if (!innerAnnotationReading.parameter.isPrimitive()) {
                    var attribute = innerAnnotationReading.parameter().getDeclaredFields();

                    for (int i = 0; i < attribute.length; i++) {
                        var annotation = attribute[i].getAnnotations();

                        for (int j = 0; j < annotation.length; j++) {
                            if (annotation[j].annotationType().getName().matches("(.*)Edad")) {
                                Edad validarEdad = null;
                                try {
                                    validarEdad = innerAnnotationReading.parameter()
                                            .getDeclaredField(attribute[i].getName())
                                            .getAnnotation(Edad.class);
                                } catch (NoSuchFieldException | SecurityException e) {
                                    e.printStackTrace();
                                    return status;
                                }
                                if (validarEdad != null) {
                                    status = ValidarUtil.edad(model, attribute[i].getName(),
                                            validarEdad.minima());
                                    if (!status)
                                        return status;
                                }
                            } else if (annotation[j].annotationType().getName().matches("(.*)Telefono")) {
                                Telefono telefono = null;
                                try {
                                    telefono = innerAnnotationReading.parameter()
                                            .getDeclaredField(attribute[i].getName())
                                            .getAnnotation(Telefono.class);
                                } catch (NoSuchFieldException | SecurityException e) {
                                    e.printStackTrace();
                                    return status;
                                }
                                if (telefono != null) {
                                    status = ValidarUtil.telefono(model, attribute[i].getName(),
                                            telefono.longitud());
                                    if (!status)
                                        return status;
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "[[ No esta permitido la anotación @Validar en un tipo primitivo ]]",
                            "Annotation @Validar",
                            JOptionPane.ERROR_MESSAGE);
                    status = !status;
                }
            }
            return status;
        });

        if (resultado) {
            try {
                Method m = controller.getClass().getMethod(method, model.getClass());
                try {
                    return m.invoke(controller, model);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }
        return resultado;
    }

}
