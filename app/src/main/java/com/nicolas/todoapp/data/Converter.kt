package com.nicolas.todoapp.model

import androidx.room.TypeConverter

class Converter {

    /* Room solo permite almacenar solo datos de tipo primitivo, en este proyecto contamos con un dato de tipo Priority, el cual es una referencia
    para poder agregar este dato a la base de datos, necesitamos utilizar un conversor, el cual cuenta con una funcion para convertir de ref a string
    y otra funcion para convertir de string a ref.
    Para esto se debe utilizar el anotador @TypeConverter*/

    @TypeConverter
    fun fromPriorityToString(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun fromStringToPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}