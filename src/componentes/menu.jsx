import React, { useState } from "react";
import { Link } from 'react-router-dom';
import { useAuth } from '../Login/AuthProvider';
import { useNavigate } from 'react-router-dom';
import "../estilos/menu.css"


// Componente MenuSuperior
const MenuSuperior = () => {

  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    //localStorage.removeItem('token'); // Elimina el token
    navigate('/login');
  };

  const userAcortado=()=>{
    var userMostrar=user ? user.split('@')[0]: "";
    return userMostrar
  }
  const [isNavbarCollapsed, setIsNavbarCollapsed] = useState(true);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark px-3 menu-superior" >
      <Link className="navbar-brand" to="/">
        <img
          src="/imagenes/logo-menu.png"
          alt="Supermercado"
          width="40"
          height="40"
          className="d-inline-block align-top"
        />
        <span className="mx-3 d-inline-block align-top">
          <span className="fw-bold fs-4">Comparator</span>
        </span>
      </Link>

      {/* Botón que aparece en pantallas pequeñas */}
      <button
        className="navbar-toggler"
        type="button"
        onClick={() => setIsNavbarCollapsed(!isNavbarCollapsed)}
        aria-controls="navbarNav"
        aria-expanded={!isNavbarCollapsed}
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      {/* Menú colapsable */}
      <div className={`collapse navbar-collapse ${!isNavbarCollapsed ? "show" : ""}`} id="navbarNav">
        <ul className="navbar-nav ms-auto d-flex align-items-center">
          {user === null ? (
            ""
          ) : (
            <>
              <li className="nav-item mx-2">
                <Link className="nav-link" to="/">Comparador de supermercados</Link>
              </li>
              <li className="nav-item mx-2">
                <Link className="nav-link" to="/comparador2">Comparador entre 2 supermercados</Link>
              </li>
              <li className="nav-item mx-2 d-flex align-items-center">
                <Link className="nav-link" to="/cestaCompra">Cesta de la compra</Link>
              </li>
              <li className="nav-item mx-2 d-flex align-items-center">
                <span className="saludo nav-link">Hola, {userAcortado()}</span>
              </li>
              <li className="nav-item d-flex align-items-center justify-content-center m-2" style={{width: 32, height:32}}>
                
                <div
                  onClick={handleLogout} 
                  className="btn-salir">
                </div>
                
              </li>

            </>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default MenuSuperior;
