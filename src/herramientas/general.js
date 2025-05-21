import { useAuth } from "../Login/AuthProvider";
import ServicioCesta from "../servicios/ServicioCesta";
import ServicioProductos from "../servicios/ServicioProductos";

export const filtrarPorSupermercado = (resultados, supermercadoSeleccionado) => {
    if (supermercadoSeleccionado !== "Todos los supermercados") {
        return resultados.filter(prod => prod.supermercado === supermercadoSeleccionado.toUpperCase());
    }
    return resultados;
}

export const comprobarSiEstanEnLaCesta = (productosTotal, setResultados, setError, user) => {
    ServicioCesta.getProdsCesta(user).then((respuesta) => {
        const productosEnCesta = respuesta.data?.productos || [];

        console.log('Productos en la cesta:', productosEnCesta);

        const productosActualizados = productosTotal.map(prodResultado => {
            const enCesta = productosEnCesta.some(prodCesta =>
                prodCesta.nombre === prodResultado.nombre &&
                prodCesta.supermercado === prodResultado.supermercado
            );

            return {
                ...prodResultado,
                enLaCesta: enCesta
            };
        });

        setResultados(productosActualizados);
    }).catch(() => {
        setError('Ha ocurrido un error con la conexión');
    })
}

export const cambiarImgFavoritos = (imagen, setImagen) => {
    setImagen(imagen === "/imagenes/fav1.png" ? "/imagenes/fav2.png" : "/imagenes/fav1.png");
}

export const dividirResultadosPorSupermercados = (productos, setProductosPorSupermercado) => {
    const clasificados = {
        mercadona: [],
        carrefour: [],
        dia: [],
        ahorramas: []
    };

    productos.forEach(prod => {
        const supermercado = prod.supermercado.toLowerCase();

        if (clasificados[supermercado]) {
            clasificados[supermercado].push(prod);
        }
    });
    console.log(clasificados)
    setProductosPorSupermercado(clasificados);
};


export const obtenerIdProducto = (producto) => `producto-cesta-${producto.supermercado.replace(' ', '')}-${producto.index}`
