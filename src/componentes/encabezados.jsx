
const Encabezado = ({ titulo, texto1, texto2, img }) => {
  return (
    <div>
      <div className="header-box my-4 d-flex flex-column flex-md-row flex-wrap justify-content-between align-items-center rounded p-4">
        <div className="w-75">
          <h2 className="mb-3 fs-2">{titulo}</h2>
          <div className="mb-0">
            {texto1}
          </div>
        </div>
        <img
          src={img}
          alt="Logo Comparador"
          className="ms-3"

          style={{width: '170px'}}
        />
      </div>

      {texto2 && (
        <section className="search-section shadow-sm rounded p-3 my-4">
          <p className="mb-0 fs-5 fw-bold text-center">{texto2}</p>
        </section>
      )}
    </div>
  )
}

export default Encabezado;