import httpExterno from "./http-externo";
import http from "./http-axios"

const token = localStorage.getItem("token");

class ServicioProductos {
  buscarProducto(nombre) {
    return httpExterno.get(`/productos/precioGranel/${nombre}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    });
  }

  prods(){
    return http.get("/productos", {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    })
  }

  prodsCesta(){
    return http.get("/productosCesta", {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    })
  }

  buscarProductoSupermercadosConcretos(nombre, supermercados){
    return httpExterno.get(`/productos/precioGranel/${nombre}/${supermercados}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    })
  }

  buscarCesta(correoUsuario){
    return httpExterno.get(`/productos/${correoUsuario}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    })
  }
}

export default new ServicioProductos();