
const ModalEliminarProducto = ({producto, onClose, eliminarProd}) => {

    const eliminarProducto = () => {
        //Llamada al servicio mandándole el id del producto junto al de la lista
        eliminarProd(producto);
        onClose()
    }

    return (
        <div className="d-flex flex-column align-items-center justify-content-center">
            <div className="p-3">
                Está apunto de eliminar <strong>{producto.nombre}</strong> de la lista
                ¿Está seguro de que quiere hacerlo?</div>
            <img 
                src="/imagenes/papelera_icon.png" 
                alt="icono papelera" 
                className="w-50 m-2"
                />
            <div className="d-flex flex-column flex-sm-row justify-content-center justify-content-sm-between align-items-center gap-2">
                <button onClick={eliminarProducto} className="btn btn-danger">Eliminar producto</button>
                <button onClick={onClose} className="btn btn-success">Mantener producto</button>
            </div>
        </div>
    )
}

export default ModalEliminarProducto