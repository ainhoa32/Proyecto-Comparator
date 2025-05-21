import React, { useState } from 'react';
import ServicioProductos from '../servicios/ServicioProductos'; // Asegúrate de importar correctamente el servicio
import ResultadoBusqueda from './resultadoBusqueda';
import Encabezado from './encabezados';

const Comparador2 = () => {
  const [producto, setProducto] = useState('');
  const [super1, setSuper1] = useState('');
  const [super2, setSuper2] = useState('');
  const [resultados, setResultados] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const titulo = "Comparator, tu comparador de confianza"
  const textoEncabezado1 = "Compara precios entre 2 supermercados, de esta manera podrás elegir entre los establecimientos que están más cerca de ti."
  const textoEncabezado2 = "Busca un producto y filtra por los supermercados que quieres comparar"

  const buscarResultados = async (e) => {
    e.preventDefault();
    if (!producto.trim()) {
      setError("Introduzca el nombre de un producto.")
      setResultados([])
    } else if (!super1.trim() || !super2.trim()) {
      setError("Introduzca 2 supermercados a comparar.")
      setResultados([])
    } else if ((super1 === super2)) {
      setError("Introduzca 2 supermercados diferentes.")
      setResultados([])
    } else {
      setLoading(true); // comienza la carga


      ServicioProductos.buscarProductoSupermercadosConcretos(producto.trim().toLowerCase(), super1 + "-" + super2).then(respuesta => {
        if (respuesta.data && respuesta.data.length > 0) {
          // Esperar 1 segundo antes de mostrar resultados
          comprobarSiEstanEnLaCesta(respuesta.data);
          setError(null);
          setLoading(false); // termina la carga después del delay
          window.scrollTo({ top: 500, behavior: 'smooth' });
        } else {
          setTimeout(() => {
            setError('No se encontraron productos.');
            setResultados([]);
            setLoading(false); // termina la carga después del delay
          }, 500);
        }
      }).catch(() => {
        setError('Ha ocurrido un error con la conexión');
        setResultados([]);
        setLoading(false); // termina la carga después del delay
      });
    }
  }

  const comprobarSiEstanEnLaCesta = (productos) => {
    ServicioProductos.prodsCesta().then((respuesta) => {
      const productosEnCesta = respuesta.data;

      const productosActualizados = productos.map(prodResultado => {
        const enCesta = productosEnCesta.some(prodCesta =>
          prodCesta.nombre === prodResultado.nombre &&
          prodCesta.supermercado === prodResultado.supermercado
        );

        return {
          ...prodResultado,
          enLaCesta: enCesta
        };
      });

      setResultados(productosActualizados); // Actualizamos el estado

    }).catch(() => {
      setError('Ha ocurrido un error con la conexión');
    })
  }

  return (
    <div className="container py-4">

      <Encabezado titulo={titulo} texto1={textoEncabezado1} texto2={textoEncabezado2} img={"imagenes/supermercados.png"} />

      <div className="text-center mb-4">
        <form onSubmit={buscarResultados} className="d-flex flex-wrap justify-content-center gap-2">
          <input
            type="text"
            placeholder="Busca el producto"
            className="form-control w-auto"
            value={producto}
            onChange={(e) => setProducto(e.target.value)}
          />
          <button type="submit" className="btn btn-success">Buscar</button>
          <select value={super1} onChange={(e) => setSuper1(e.target.value)} className="form-select" style={{ width: "30%" }}>
            <option value="">Supermercado 1</option>
            <option value="Mercadona">Mercadona</option>
            <option value="Ahorramas">Ahorra mas</option>
            <option value="Dia">Dia</option>
            <option value="Carrefour">Carrefour</option>
          </select>
          <select value={super2} onChange={(e) => setSuper2(e.target.value)} className="form-select" style={{ width: "30%" }}>
            <option value="">Supermercado 2</option>
            <option value="Mercadona">Mercadona</option>
            <option value="Ahorramas">Ahorra mas</option>
            <option value="Dia">Dia</option>
            <option value="Carrefour">Carrefour</option>
          </select>
        </form>
      </div>

      <ResultadoBusqueda producto={producto} resultados={resultados} setResultados={setResultados} loading={loading} error={error} />


    </div>
  );
};

export default Comparador2;
