import { Link } from "react-router-dom";

const Pagina404 = () => {
  return (
    <div className="p-5 m-5 d-flex flex-column align-items-center gap-4">
      <h2 className="pagina404-title">La página no ha sido encontrada</h2>
      <img src="../imagenes/img_pag_404.png" alt="imagen 404" className="w-25" />
      <Link to="/" className="btn btn-success">Volver al Inicio</Link>
    </div>
  );
};

export default Pagina404;
