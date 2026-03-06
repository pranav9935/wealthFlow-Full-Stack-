import { useNavigate } from "react-router-dom";

function RegisterSuccess() {

  const navigate = useNavigate();

  return (
    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">Success</h1>

        <p className="subtitle">
          Your profile has been created successfully.
        </p>

        <p style={{ marginBottom: "20px" }}>
          You can now login and start managing your investments.
        </p>

       <button onClick={() => navigate("/login", { replace: true })}>
          Go to Login
        </button>

      </div>

    </div>
  );
}

export default RegisterSuccess;