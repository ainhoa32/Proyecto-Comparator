import { useState } from 'react';
import ServicioProductos from '../servicios/ServicioProductos';
import 'bootstrap/dist/css/bootstrap.min.css';
import "../estilos/comparador.css"
import ResultadoBusqueda from './resultadoBusqueda';
import Encabezado from './encabezados';
import { cambiarImgFavoritos, comprobarSiEstanEnLaCesta, filtrarPorSupermercado } from '../herramientas/general';
import { useAuth } from '../Login/AuthProvider';
import BusquedasFavoritas from './busquedasFavoritas';
import { useFavoritos } from '../hooks/useFavoritos';

const Comparador = () => {

  const [producto, setProducto] = useState('');
  const [resultados, setResultados] = useState([]);
  const [supermercadoSeleccionado, setSupermercadoSeleccionado] = useState("Todos los supermercados");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [cambioBusquedasFavoritas, setCambioBusquedasFavoritas] = useState(1)

  const { user } = useAuth();

  const titulo = "Comparator, tu comparador de confianza"
  const textoEncabezado1 = "Descubre la manera más fácil y eficiente de realizar tus compras online con nuestro comparador de precios entre supermercados. ¡Ahorra tiempo y dinero en tus compras!"
  const textoEncabezado2 = "Busca un producto y lo compararemos entre varios supermercados"

  const {
    imagen,
    setImagen,
    favoritoGuardado,
    eliminarBusquedaFav,
    anadirBusquedaFav,
    setFavoritoGuardado
  } = useFavoritos(setError);


  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!producto.trim()) {
      setError("Introduzca el nombre de un producto.")
    } else {
      setLoading(true); // comienza la carga

      ServicioProductos.buscarProducto(producto.trim().toLowerCase()).then(respuesta => {
        if (respuesta.data && respuesta.data.length > 0) {
          setResultados(respuesta.data)
          setError(null);
          setLoading(false);
          comprobarSiEstanEnLaCesta(respuesta.data, setResultados, setError, user)
          window.scrollTo({ top: 500, behavior: 'smooth' });
        } else {
          setTimeout(() => {
            setError('No se encontraron productos.');
            setResultados([]);
            setLoading(false);
          }, 1000);
        }
      }).catch((error) => {
        setError('Ha ocurrido un error con la conexión');
        setResultados([]);
        setLoading(false);
      });
    }
  };

  const manejarFavoritos = () => {
    if (!producto.trim()) {
      setError("Introduzca el nombre de un producto.")
    } else {
      const busquedaFav = {
        usuario: user,
        nombreBusqueda: producto
      }

      if (favoritoGuardado) {
        eliminarBusquedaFav(busquedaFav, setCambioBusquedasFavoritas)
      } else {
        anadirBusquedaFav(busquedaFav, setCambioBusquedasFavoritas)
      }
      setCambioBusquedasFavoritas(Math.random())
    }
  }

  const handleInputChange = (e) => {
    setProducto(e.target.value)
    setFavoritoGuardado(false)
    if (favoritoGuardado) {
      cambiarImgFavoritos(imagen, setImagen)
    }
  };

  return (
    <div className="container py-4">
      <Encabezado titulo={titulo} texto1={textoEncabezado1} texto2={textoEncabezado2} img={"imagenes/compra.png"} />

      <form className="d-flex flex-wrap justify-content-center gap-2" onSubmit={handleSubmit}>
        <div style={{ width: 35, height: 35 }} className='d-flex align-items-center justify-content-center'>
          <img src={imagen} onClick={manejarFavoritos} alt="favoritos" title='Añadir búsqueda a favoritos' className='fav w-100 h-100' />
        </div>
        <input
          type="text"
          className="form-control w-50"
          placeholder="Busca el producto"
          value={producto}
          onChange={(e) => handleInputChange(e)}
        />
        <select name="supermercado" id='selectSupermercado' className='form-select w-auto' onChange={(e) => setSupermercadoSeleccionado(e.target.value)}>
          <option value="Todos los supermercados">Todos los supermercados</option>
          <option value="Mercadona">Mercadona</option>
          <option value="Ahorramas">Ahorra más</option>
          <option value="Carrefour">Carrefour</option>
          <option value="Dia">Día</option>
        </select>
        <button type="submit" className="btn btn-success">Buscar</button>
      </form>

      <BusquedasFavoritas setError={setError} favoritoGuardado={favoritoGuardado} cambioBusquedasFavoritas={cambioBusquedasFavoritas} setCambioBusquedasFavoritas={setCambioBusquedasFavoritas} />

      <ResultadoBusqueda producto={producto} resultados={filtrarPorSupermercado(resultados, supermercadoSeleccionado)} setResultados={setResultados} loading={loading} error={error} setError={setError}/>

    </div>
  );
};

export default Comparador;
