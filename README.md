# lazy_ledger
# Requerimientos del proyecto
  Requisitos Funcionales(RF).
    Gestión de usuarios. 
      R.F-01. El sistema debe permitir a los usuarios nuevos crear una cuenta utilizando correo electrónico y contraseña. Condición: Siempre y cuando el correo no se haya usado previamente se creará la cuenta, caso contrario se mostrará un mensaje         de error que diga, Vaya.. El correo ingresado ya se encuentra actualmente en uso.
      R.F-02. El sistema auténtica las credenciales del usuario.
      R.F-03. El sistema debe permitir a los usuarios iniciar sesión usando las credenciales(correo electrónico y contraseña).
      R.F-04. El sistema debe permitir la gestión del perfil del usuario (editar nombre, correo, contraseña).
      Gestión de Libros.
      R.F-05. El sistema debe permitir al usuario autenticado crear un nuevo libro contable. Condición: Al momento de crear el libro contable el usuario tiene el rol de Owner. 
      R.F-06. El Owner (usuario que creó el libro) puede invitar a otros usuarios. Condición: Únicamente se podrá invitar usuarios que se encuentren registrados en la app.
      R.F-07. El Owner podrá asignar roles dentro del libro contable(analista o asistente) a los usuarios los cuales vayan a formar parte del libro contable.
      R.F.08. El Owner podrá gestionar el libro contable(Editar, Invitar, Eliminar, Agregar).

  Requisitos No Funcionales (RNF)
    Seguridad.
      R.NF-01. Todas las contraseñas de usuario deben almacenarse hasheadas y salteadas (ej. bcrypt)
      R.NF-02. Toda la comunicación externa con la API debe ser a través de HTTPS.
      R.NF-03. El acceso a los datos de un Ledger debe estar estrictamente limitado a sus miembros autorizados.
    Rendimiento
      R.NF-04. Las respuestas de la API para consultas de listas no deben superar los 500ms en condiciones de carga media.
      R.NF-05. El tiempo total desde que un usuario envía un audio hasta que recibe la confirmación no debe superar los 5 segundos.
    Usabilidad
      R.NF-06. El bot de Telegram debe proporcionar menús y botones para guiar al usuario en las acciones comunes.
      R.NF-07. Los mensajes de error presentados al usuario deben ser claros y no técnicos.
    Disponibilidad
      R.NF-08. El servicio debe aspirar a una disponibilidad del 95%.
    Escalabilidad
      R.NF-09. La arquitectura debe ser capaz de soportar un incremento de 10x en el número de usuarios y transacciones sin necesidad de un rediseño fundamental.

# Arquitectura seleccionada.
# Estándares de codiﬁcación adoptados.
# Flujo de trabajo con GitFlow.
# Instrucciones de ejecución.
