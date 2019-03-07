En el sistema de “enrolment” utilizamos un atributo de tipo Boolean “isOut” para saber si está dentro o fuera de la cofradía. El “enrolment” lo solicita el actor “member” y las cofradías tienen una lista de “enrolment” donde pueden aceptar/rechazar. 


En “enrolment” asumimos que si una cofradía desenrola a un miembro, este último no puede hacer una nueva “enrolment”.


Cuando se realiza una de las 2 acciones, el Boolean se inicializa a true o false. Si se rechaza el enrolment, “isOut” se pone como false y se registra el momento en el que abandona la cofradía. El “enrolment” no se borra y se permite que el member que vuelva a solicitar acceso.


El borrado de boxes es recursivo, mensajes incluidos. Borrando una “MessageBox” se borran todos sus hijas y los mensajes que todas ellas contuviesen.


Cuando se crea un actor de tipo admin no se tienen que aceptar los términos y condiciones ya que la cuenta se crea por un tercero.



Se ha delimitado el tamaño de una procesión a lo ancho mediante el atributo “maxCols”, pudiendo tener un número de filas infinito. El lugar donde se recomienda que se asigne a un nuevo “member” para una “march” es la primera posición no ocupada empezando desde la posición (1,1).


En el mensaje de broadcast, el “recipient” y el “sender” son el mismo para diferenciarlos de los mensajes normales. Ese mensaje aparecería en el “outbox” del admin y en las “notification boxes” de todos los actores.


Cuando se crea una procesión, el mensaje de aviso de broadcast tiene como sender y recipient la cofradía que la ha creado.


Para calcular el spam se han añadido los atributos boolean “isSpam” a mensajes y actores para evitar una carga excesiva en el momento en el que se quiera calcular los actores spammer. De esta manera solo hay que mirar los boolean “isSpam” de los mensajes y calcular según el % especificado en los requisitos.


Cada actor tiene un finder que se va vaciando (su lista de procesiones resultados) y no se crean nuevos finders.


Respecto al Item 2, se facilita documentación para la instalación del certificado creado por nosotros (por lo que el navegador indicará que es un certificado no seguro. Asumimos que el profesorado dispone de una carpeta servers preparada para soportar https.


Los nombres originales de las entidades “float” y “area” se han cambiado por “platform” y “zone” para evitar conflictos con las clases de Java.


Asumimos que, tal y como se indica en Lecture Notes, las funcionalidades de exportación de datos y eliminación de datos de usuarios de la base de datos son de recomendada realización pero no obligatorias.


Cuando se dice que un admin puede agregar más prioridades de mensajes además de las que vienen por defecto, estas prioridades las pueden utilizar todos los usuarios.


Para el patrón del ticker de procesión utilizamos el patrón indicado en el requisito nº 4 y no el indicado en el nº 17.


Respecto a las notificaciones:

- Cuando una brotherhood echa a un member: el metodo, “changeStatusNotification” envía un mensaje a la outBox del principal(Brotherhood) y a la “notificationBox” del member afectado. El método “notificationMessageEnrolAMember” se encarga de la notificacion cuando una brother acepta a un member.

- “notificationOutMember” envía un mensaje a la “outBox” del principal(member) y a la “notification box” de la brotherhood que se ha salido el member

- El método notificationPublishProcession envía un mensaje a la outBox del principal(brotherhood) y a todas las notification box de los actores registrados en el sistema. El “recipient” y el “sender” de este mensaje es el propio actor principal (brotherhood), siguiendo el mismo criterio que en broadcast
