import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';
import ServicioUsuario from '../servicios/ServicioUsuario';
import bcrypt from 'bcryptjs';

const Login = () => {
  const [usuario, setUsuario] = useState('');
  const [password, setPassword] = useState('');

  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {

    e.preventDefault();

    ServicioUsuario.login(usuario)
      .then((response) => {
        const user = response.data[0];
        const passwdHash = user.pass;
        let ContraseñaCorrecta = bcrypt.compareSync(password, passwdHash)
        if (ContraseñaCorrecta) {
          login(user.nombre);
          navigate('/');
        } else {
          setError("Usuario no es correcto")
        }


      })
      .catch((error) => {
        alert(error)
        navigate('/login');
      });
  };

  return (
    <section className="min-vh-100 d-flex align-items-center">
      <div className="container">
        <div className="row justify-content-center align-items-center ">
        <div className="col-md-5 text-center justify-content-center">
        {/* Imagen de fondo que ocupa toda la columna */}
            <img
              src="/imagenes/logoapp.png"
              alt="logo"
              className="w-100 h-100 overflow-hidden"
              style={{
                objectFit: 'cover',
              }}
            />
          </div>
          {/* Bloque formulario */}
          <div className="col-md-5">
            <form onSubmit={handleSubmit} className="p-4 rounded bg-gold shadow-sm">
              <h1 className="mb-4 text-center text-gold">
                Comparator
              </h1>
              <h2 className="text-center text-purple mb-4">Identifícate</h2>
              <div className="card bg-cream border-0">
                <div className="card-body">
                  <div className="mb-3">
                    <label htmlFor="usuario" className="form-label text-purple">Usuario:</label>
                    <div className="input-group">
                      <input
                        type="text"
                        className="form-control"
                        id="usuario"
                        placeholder="ainhoa"
                        value={usuario}
                        onChange={(e) => setUsuario(e.target.value)}
                        required
                      />
                    </div>
                  </div>

                  <div className="mb-3">
                    <label htmlFor="password" className="form-label text-purple">Contraseña:</label>
                    <div className="input-group">
                      <input
                        type="password"
                        className="form-control border-purple"
                        id="password"
                        placeholder="*******"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                      />
                    </div>
                  </div>

                  <div className="form-check mb-3">
                    <input type="checkbox" className="form-check-input" id="recordarme" />
                    <label className="form-check-label text-purple" htmlFor="recordarme">Recuérdame</label>
                  </div>

                  {error && <div className="alert alert-danger">{error}</div>}

                  <div className="text-center">
                    <button type="submit" className="btn btn-purple btn-lg rounded-pill">Acceder</button>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Login;
