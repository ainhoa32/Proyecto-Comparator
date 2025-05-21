import { useEffect, useState } from "react";
import ServicioBusquedas from "../servicios/ServicioBusquedas";
import { useAuth } from "../Login/AuthProvider";
import { useFavoritos } from "../hooks/useFavoritos";

const BusquedasFavoritas = ({setError, cambioBusquedasFavoritas, setCambioBusquedasFavoritas}) => {

    const { user } = useAuth();
    const {eliminarBusquedaFav} = useFavoritos(setError);
    const [busquedasFavs, setBusquedasFavs] = useState([])

    useEffect(() => {
        ServicioBusquedas.getBusquedasFavs(user).then((response) => {
            setBusquedasFavs(response.data)
        }).catch(() => {
            setError('Ha ocurrido un error con la conexión');
        })
    }, [cambioBusquedasFavoritas])

    const eliminarFavorito = (producto) => {
        const busqueda = {
            usuario: user,
            nombreBusqueda: producto
        }
        eliminarBusquedaFav(busqueda, setCambioBusquedasFavoritas)
    }

    return (
        <div>
            {busquedasFavs.length > 0 && (
                <div className="d-flex flex-wrap px-1 my-5 align-items-center justify-content-center gap-2">
                    <div>Búsquedas favoritas: </div>
                    {busquedasFavs.map((busqueda, index) => (
                        <button key={index} className="btn btn-light border">
                            <div>
                                <span className="me-2" onClick={() => eliminarFavorito(busqueda.nombreBusqueda)}>&times;</span>
                                <span>{busqueda.nombreBusqueda}</span>
                            </div>
                        </button>
                    ))}
                </div>
            )}
        </div>
    )
}

export default BusquedasFavoritas;