Este proyecto es una API Rest basada en SpringBoot, que consta de dos funciones que permiten crear un usuario y consultar por los 
datos de un usuario ya creado .La espepecificación de estos metodos se indica mas abajo en este documento.

Las librerias y frameworks utilizados en esta api fueron :

- SpringBoot
- Swagger
- Jwt
- Lombok
- JPA
- Groovy y Spock para los Test
- H2 como base de datos

/*****************************DOCUMENTACION DEL PROYECTO**************************************/

La documentación del proyecto se encuentra dentro deL directorio Diagramas en el path raiz del proyecto

/******************COMPILACION Y EJECUCION DEL PROYECTO*****************/

Para compilacion del proyecto

  1-. entrar al directorio base del proyecto

  2.- ejecutar el comando : gradlew bootJar

  3.- Este comando dejara el binario del proyecto SpringBoot (.jar) en [directorioBaseProyecto]/build/libs

Para ejecucion del proyecto

   1-. entrar al directorio base del proyecto

  2.- ejecutar el comando : gradlew bootRun

  3.- Lo anterior levantara la aplicacion Springboot, dejando disponible los servicios que se indican en la seccion "METODOS REST DISPONIBLES"

  /****************TEST*******************/

  Los test estan codoficados  en groovy con el framework Spock (clase UserServiceTest en el proyecto)

 /***************************USO DE SERVICOS REST API*********************************/

Para el uso del servicio  en la misma maquina de ejecucion debe partir con la siguiente url en el cliente que se utilizando

http://localhost:8080/evaluacion/[nombre de metodo]

/*************SWAGGER ******************************************: /

Esta Api puede probarse tambien a traves de Swagger que se accede de con la siguiente url en un navegador

http://localhost:8080/swagger-ui/

Tambien en la raiz del proyecto se incluye un archivo para poder ejecutar los servicios en postman,
con el archivo :

- EvaluacionBCI.postman_collection.json


/*******ESPECIFICACION METODOS REST *******************/

   
La api dispone de 2 metodos que cuyos ejemplos de uso se indican  a continuacion:

1.'   /create : Permite el registro de un usuario con los telefonos asociados solo si este no existe. La busqueda de existencia se realiza por email

Ejemplo de Json de entrada

    {

    "name":"Roberto",

    "email":"roberto@gmail.com",
    
    "password":"a2asfGfdfdf4",
    
    "phones": [{"number":12323,"citycode":12,"contrycode":"56"}]
    
    }

En el caso de exito la salida debe ser la siguiente:

    {
        "id": 2,
        
    "created": "2023-11-07T20:13:07.796+00:00",
    
    "lastLogin": "2023-11-07T20:13:07.797+00:00",
    
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JlcnRvQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiYTJhc2ZHZmRmZGY0IiwiaWF0IjoxNjk5Mzg3OTg3LCJleHAiOjE2OTkzOTE1ODd9.Q9BKIa5mjxpUU8uZm-diLeKNY5isHMwolen_N97ErMs",
    
    "active": true
    
    }


En el caso que el email ya este asociado a un usuario se entregará el error siguiente:

{

"timestamp": "2023-12-07T14:55:11.295+00:00",

"codigo": 100,

"detail": "Usuario ya existe",

"fieldErrors": []

}

Restricciones:

 a) El password debe tener solo una Mayúscula y solamente dos números,
  en combinación de letras minúsculas, largo máximo de 12 y mínimo 8. "a2asfGfdfdf4" como ejemplo .En caso contrario se indicara el error correspondiente
 
b) El email debe cumplir con el formato aaaaaaa@undominio.algo,En caso contrario se indicara el error correspondiente

c) Un usuario se identifica en el sistema solo por su email.

2.-  /update : Permite realizar la actualizacion de los datos de un usuario en base a su email registrado

Ejemplo  de entrada del servicio:

Este servicio debe enviarse como parámetro  los datos de usuario que se van a actualizar.

 Ejemplo de entrada : 

{

    "name":"Roberto",

    "email":"roberto@gmail.com",
    
    "password":"a2asfGfdfdf4",
    
    "phones": [{"number":12323,"citycode":12,"contrycode":"56"}],

    "active":true
    
    }



En el caso que el email no sea valido, osea no este asociado a un usuario el resultado debe ser el siguiente:


  {

    "timestamp": "2023-12-07T14:10:40.440+00:00",
    "codigo": 100,
    "detail": "Usuario no existe",
    "fieldErrors": []
  }

3.- /read : Permite  obtener los datos de usuario en base a un email de usuario registrado con el método create

Este servicio debe enviarse como parametro con el email del usuario a buscar


nombre parametro em header: "email"

valor parametro : "usuario@email.com"

Si el email ya esta asociado a un usuario en el sistema, se retornará la siguiente informacion:


{

"id": 1,

"name": "Roberto2",

"email": "roberto3@gmail.com",

"password": "a2asfGfdfdf4",

"phones": [{"number": 12323,"citycode": 12,"contrycode": "56"}],

"created": "2023-12-07T14:45:57.339+00:00",

"lastLogin": "2023-12-07T14:45:57.372+00:00",

"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JlcnRvM0BnbWFpbC5jb20iLCJwYXNzd29yZCI6ImEyYXNmR2ZkZmRmNCIsImVtYWlsIjoicm9iZXJ0bzNAZ21haWwuY29tIiwiaWF0IjoxNzAxOTYwMzU3LCJleHAiOjE3MDE5NjM5NTd9.Cm55kSZGwlwGAsAMKvcXNsqVWaOyxhKXr8UUnpg5LPU",

"active": true

}





En el caso que el email no sea valido, osea no este asociado a un usuario el resultado debe ser el siguiente:


{

    "timestamp": "2023-12-07T14:10:40.440+00:00",
    "codigo": 100,
    "detail": "Usuario no existe",
    "fieldErrors": []
}

4.- /delete: Permite elimnar los datos asociados a un usuario. La busqueda se por el 
email registrado con la funcion create.

Si el email existe retorna un status de operacion existosa (200)

En el caso que el email no sea válido, osea no este asociado a un usuario el resultado debe ser el siguiente:

{

    "timestamp": "2023-12-07T14:10:40.440+00:00",
    "codigo": 100,
    "detail": "Usuario no existe",
    "fieldErrors": []
}





