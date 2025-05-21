import "../estilos/login.css"
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';
import ServicioUsuario from '../servicios/ServicioUsuario';

const Login = () => {

    const switchers = [...document.querySelectorAll('.switcher')]

    switchers.forEach(item => {
        item.addEventListener('click', function () {
            switchers.forEach(item => item.parentElement.classList.remove('is-active'))
            this.parentElement.classList.add('is-active')
        })
    })

    const [loginUsuario, setLoginUsuario] = useState('');
    const [loginPassword, setLoginPassword] = useState('');

    const [signupUsuario, setSignupUsuario] = useState('');
    const [signupPassword, setSignupPassword] = useState('');
    const [signupPasswordConfirm, setSignupPasswordConfirm] = useState('');


    const [errorLogin, setErrorLogin] = useState('');
    const [errorSignup, setErrorSignup] = useState('');

    const { login } = useAuth();
    const navigate = useNavigate();

       const esEmailValido = (email) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    console.log(regex.test(email))
    return regex.test(email);
};

    const handleSubmitLogin = async (e) => {
        e.preventDefault();

         if (!esEmailValido(loginUsuario)) {
        setErrorLogin("Introduce un correo electrónico válido");
        return;
    }

        try {
            const respuesta = await ServicioUsuario.login({
                nombre: loginUsuario.toLowerCase(),
                contrasena: loginPassword
            });
            // Ahora response.data será el token JWT si el login fue exitoso
            if (respuesta.status === 200) {
                const token = respuesta.data;
                localStorage.setItem('token', token);  // Guardamos el JWT
                login(loginUsuario);
                navigate("/")
            } else {
                setErrorLogin("Usuario o contraseña incorrectos");
            }
        } catch (error) {
            setErrorLogin("Error al iniciar sesión");
        }
    };

   const handleSignupSubmit = async (e) => {
    e.preventDefault();

     if (!esEmailValido(signupUsuario)) {
        setErrorSignup("Introduce un correo electrónico válido");
        return;
    }

    if (signupPassword !== signupPasswordConfirm) {
        setErrorSignup('Las contraseñas no coinciden');
        return;
    }

    if (signupPassword.length < 6) {
        setErrorSignup('La contraseña debe tener al menos 6 caracteres');
        return;
    }
    try {
        const respuesta = await ServicioUsuario.registrar({
            nombre: signupUsuario.toLowerCase(),
            contrasena: signupPassword
        });

        if (respuesta.status === 201) {
            alert("Usuario registrado con éxito. Ahora puedes iniciar sesión.");
            setSignupUsuario('');
            setSignupPassword('');
            setSignupPasswordConfirm('');
            setErrorSignup('');
        } else {
            setErrorSignup("Error desconocido al registrar usuario");
        }
    } catch (err) {
        if (err.response && err.response.status === 400) {
            setErrorSignup("El nombre de usuario ya existe");
        } else {
            setErrorSignup("Error al registrar usuario");
        }
    }

 

    }


    return (
        <div className="p-4 m-5">
            <div className="d-flex flex-column flex-md-row align-items-center">
                <div className="header-box col-12 col-md-6 rounded p-5 h-100" >
                    <h1 className="p-4 text-center">Comparator</h1>
                    <h3>Descubre la forma más inteligente de hacer la compra</h3>
                    <p>En Comparator podrás comparar precios de productos en diferentes supermercados para ahorrar tiempo y dinero.
                        Crea tu cuenta para guardar tus cestas, realizar búsquedas personalizadas y encontrar siempre la mejor oferta.</p>
                </div>
                <section className="col-12 col-md-6 py-3 d-flex flex-column justify-content-center align-items-center">
                    <div className="d-flex">
                        <div className="form-wrapper is-active w-50">
                            <button type="button" className="switcher switcher-login">
                                Login
                                <span className="underline"></span>
                            </button>
                            <form onSubmit={handleSubmitLogin} className="form form-login rounded overflow-hidden">
                                <fieldset>
                                    <div className="input-block">
                                        <label htmlFor="login-email">E-mail:</label>
                                        <input
                                            id="login-email"
                                            type="text"
                                            className="form-control w-100 my-3 px-2"
                                            placeholder="ainhoa@ejemplo.com"
                                            value={loginUsuario}
                                            onChange={(e) => setLoginUsuario(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div className="input-block">
                                        <label htmlFor="login-password">Contraseña: </label>
                                        <input
                                            type="password"
                                            className="form-control w-100 my-3 px-2"
                                            id="login-password"
                                            placeholder="*******"
                                            value={loginPassword}
                                            onChange={(e) => setLoginPassword(e.target.value)}
                                            required
                                        />
                                    </div>

                                    {errorLogin && <div className="alert alert-danger">{errorLogin}</div>}

                                    <button type="submit" className="btn-login btn btn-success">Acceder</button>
                                </fieldset>
                            </form>
                        </div>
                        <div className="form-wrapper w-50">
                            <button type="button" className="switcher switcher-signup">
                                Sign Up
                                <span className="underline"></span>
                            </button>
                            <form className="form form-signup rounded overflow-hidden" onSubmit={handleSignupSubmit}>
                                <fieldset>
                                    <div className="input-block">
                                        <label htmlFor="signup-email">E-mail:</label>
                                        <input
                                            id="signup-email"
                                            type="text"
                                            className="form-control w-100 my-3 px-2"
                                            placeholder="ainhoa@ejemplo.com"
                                            value={signupUsuario}
                                            onChange={(e) => setSignupUsuario(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div className="input-block ">
                                        <label htmlFor="signup-password">Contraseña:</label>
                                        <input
                                            type="password"
                                            className="form-control w-100 my-3 px-2"
                                            id="signup-password"
                                            placeholder="*******"
                                            value={signupPassword}
                                            onChange={(e) => setSignupPassword(e.target.value)}
                                            required
                                        />
                                    </div>
                                    <div className="input-block">
                                        <label htmlFor="signup-password-confirm">Confirme la contraseña:</label>
                                        <input
                                            type="password"
                                            className="form-control w-100 my-3 px-2"
                                            id="signup-password-confirm"
                                            placeholder="*******"
                                            value={signupPasswordConfirm}
                                            onChange={(e) => setSignupPasswordConfirm(e.target.value)}
                                            required
                                        />
                                    </div>
                                    {errorSignup && <div className="alert alert-danger">{errorSignup}</div>}

                                    <button type="submit" className="btn-signup btn btn-success">Continue</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    )
}

export default Login;