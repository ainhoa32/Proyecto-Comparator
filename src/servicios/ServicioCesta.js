import httpExterno from "./http-externo";

const token = localStorage.getItem("token");

class ServicioCesta {

  anadirProdCesta(producto) {
    return httpExterno.post(`/cesta/agregar`, producto, {
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
  }

  eliminarProdCesta(producto) {
    return httpExterno.delete(`/cesta/eliminar`, {
      data: producto,
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
  }

  getProdsCesta(user) {
    return httpExterno.get(`/cesta/${user}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    });
  }
}

export default new ServicioCesta();