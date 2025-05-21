import { useEffect, useState } from "react";
import EstadoBusqueda from "./estadoBusqueda";
import ModalEliminarProducto from "./modalEliminarProducto";
import ModalEliminarLista from "./modalEliminarLista";
import Modal from "./modal";
import "../estilos/transicion.css"
import ServicioProductos from '../servicios/ServicioProductos';
import { dividirResultadosPorSupermercados, obtenerIdProducto } from "../herramientas/general";
import ServicioCesta from "../servicios/ServicioCesta";
import { useAuth } from "../Login/AuthProvider";

const ResultadoBusquedaCesta = () => {

    const [childrenModal, setChildrenModal] = useState(null)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const openModal = () => setIsModalOpen(true)
    const closeModal = () => {
        setIsModalOpen(false)
        setChildrenModal(null)
    }

    const { user } = useAuth()
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [eliminando, setEliminando] = useState(null);

    const [productosPorSupermercado, setProductosPorSupermercado] = useState({
        Mercadona: [],
        Carrefour: [],
        Dia: [],
        Ahorramas: []
    });

    useEffect(() => {
        setLoading(true)
        ServicioCesta.getProdsCesta(user).then(respuesta => {
            let prods = respuesta.data.productos
            //ServicioProductos.prods().then(respuesta => {
            //ServicioProductos.buscarCesta(user).then(respuesta => {
            //ServicioUsuario.prods().then(respuesta => {
            console.log(prods)
            if (prods && prods.length > 0) {
                console.log(prods)
                dividirResultadosPorSupermercados(prods, setProductosPorSupermercado)
                setError(null);
                setLoading(false); // termina la carga después del delay
                console.log(loading)
            } else {
                setError('No se encontraron productos.');
                setProductosPorSupermercado({
                    Mercadona: [],
                    Carrefour: [],
                    Dia: [],
                    Ahorramas: []
                });
                setLoading(false);
            }
        })
            .catch(() => {
                setError('Ha ocurrido un error con la conexión');
                setProductosPorSupermercado({
                    Mercadona: [],
                    Carrefour: [],
                    Dia: [],
                    Ahorramas: []
                });
                setLoading(false);
            })
    }, [])

    const eliminarProductoConAnimacion = (producto) => {
        setEliminando(obtenerIdProducto(producto));
        closeModal();
        setTimeout(() => {
            setProductosPorSupermercado(prev => {
                const nuevos = { ...prev };
                const idSuper = producto.supermercado.toLowerCase();
                nuevos[idSuper] = nuevos[idSuper].filter(p => p.index !== producto.index);
                return nuevos;
            });
            setEliminando(null);
        }, 500);
    };

    const abrirModalEliminarLista = (lista) => {
        setChildrenModal(<ModalEliminarLista lista={lista} onClose={closeModal} />)
        openModal()
    }


    const abrirModalEliminarProducto = (producto) => {
        setChildrenModal(<ModalEliminarProducto producto={producto} onClose={closeModal} eliminarProd={eliminarProductoConAnimacion} />)
        openModal()
    }

    return (
        <div>
            <EstadoBusqueda loading={loading} error={error} resultados={productosPorSupermercado} />

            {productosPorSupermercado && !loading && (
                <section className='p-3 shadow-sm border rounded'>
                    <div className='d-flex gap-3 justify-content-center py-4'>
                        <div className='d-flex align-items-center'><span>Si ya has realizado la compra...</span></div>
                        <button className='btn btn-danger' onClick={() => abrirModalEliminarLista()}>Descartar lista</button>
                    </div>
                    {Object.entries(productosPorSupermercado).map(([nombreSupermercado, productos], indexSuper) => (
                        <div key={indexSuper}>
                            {productos.length > 0 && (
                                <div className='shadow-sm border rounded mb-4'>
                                    <img
                                        src={`imagenes/${nombreSupermercado.toUpperCase()}_NOMBRE.svg`}
                                        alt={nombreSupermercado}
                                        className='mt-5 ms-5'
                                        style={{ height: 30 }} />
                                    <div className='d-flex overflow-auto align-items-stretch gap-3 m-4'>
                                        {productos.map((item, indexProd) => (
                                            <div
                                                key={indexProd}
                                                className={`product-card my-3${eliminando === obtenerIdProducto(item) ? ' fade-up' : ''}`}
                                                id={obtenerIdProducto(item)}
                                                style={{ viewTransitionName: obtenerIdProducto(item) }}
                                            >
                                                <div className="card p-3 shadow-sm h-100 d-flex flex-column justify-content-between"
                                                    style={{
                                                        width: 250,
                                                    }}>
                                                    <div className="d-flex justify-content-center">
                                                        <img
                                                            src={item.urlImagen}
                                                            className="p-3"
                                                            alt={item.nombre}
                                                            style={{ maxHeight: 200 }}
                                                        />
                                                    </div>
                                                    <div className="text-center mt-auto">
                                                        <p className="mb-2 fs-6 fw-bold">{item.nombre}</p>
                                                        <p className="my-1 mx-1">
                                                            Precio: <strong>{item.precio}€</strong>
                                                        </p>
                                                        <p>
                                                            Precio a granel: <strong>{item.precioGranel} €/{item.unidadMedida}</strong>
                                                        </p>
                                                        <button type="button" className={`btn btn-danger delete-btn-${item.index}-${item.supermercado}`} onClick={() => abrirModalEliminarProducto(item)}>Eliminar de la cesta</button>
                                                    </div>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}
                        </div>
                    ))}
                </section>
            )}

            <Modal isOpen={isModalOpen} onClose={closeModal}>
                {childrenModal}
            </Modal>

        </div>
    )
}

export default ResultadoBusquedaCesta;