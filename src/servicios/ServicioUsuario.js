import httpExterno from "./http-externo.js";

class ServicioUsuario {
  
  login(usuario) {
    return httpExterno.post(`/usuarios/login`, usuario);
  }

  registrar(usuario) {
    return httpExterno.post(`/usuarios/registro`, usuario);
  }

  obtenerDatosProtegidos() {
    const token = localStorage.getItem("token");
    return httpExterno.get("/ruta-protegida", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}

export default new ServicioUsuario();
