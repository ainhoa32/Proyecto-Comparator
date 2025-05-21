import httpExterno from "./http-externo";

const token = localStorage.getItem("token");

class ServicioBusquedas {
  anadirBusquedaFav(favoritoAnadir) {
    return httpExterno.post(`/favoritos`,favoritoAnadir, {
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
  }

  eliminarBusquedaFav(favoritoEliminar) {
    return httpExterno.delete(`/favoritos`, {
      data: favoritoEliminar,
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
  }

  getBusquedasFavs(user) {
    return httpExterno.get(`/favoritos/${user}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      }
    })
  }


}

export default new ServicioBusquedas();
