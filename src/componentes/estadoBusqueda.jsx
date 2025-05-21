const EstadoBusqueda = ({loading, error, resultados}) => {
    return (
        <div className="my-5">
            {!loading && !error && resultados.length === 0 && (
                <div className="text-center py-5">
                    <img src="./imagenes/buscador.png" alt="Empieza la búsqueda" className='w-25' />
                    <div className='textoCarrito fw-semibold'>!Empieza ya la búsqueda!</div>
                </div>
            )}

            {loading && (
                <div className="text-center my-4">
                    <img src="./imagenes/loading.gif" alt="Cargando..." className='loading' style={{height: 130}}/>
                </div>
            )}

            {/* Error */}
            {error && !loading && (
                <div className="d-flex justify-content-center my-4">
                    <div className='alert alert-danger text-center w-50'>
                        {error}
                    </div>
                </div>
            )}
        </div>
    )
}

export default EstadoBusqueda;