import { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate, useLocation } from "react-router-dom";
import { toast } from "react-toastify";


function VerifyOtp() {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;
  const [otp, setOtp] = useState("");
  const [loading, setLoading] = useState(false);
  const [timer, setTimer] = useState(60);
  const [canResend, setCanResend] = useState(false);
  useEffect(() => {
    if (!email) {
      navigate("/register", { replace: true });
    }
  }, [email, navigate]);

  useEffect(() => {
    if (timer <= 0) {
      setCanResend(true);
      return;
    }

    const interval = setInterval(() => {
      setTimer(prev => prev - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, [timer]);

  const handleVerify = async e => {
    e.preventDefault();
    setLoading(true);

    try {
      await api.post("/auth/verify-otp", { email, otp });
      toast.success("Email verified successfully");

      setTimeout(() => {
        navigate("/login");
      }, 1000);
    } catch (err) {
      toast.error(err.response?.data || "Invalid or expired OTP");
    } finally {
      setLoading(false);
    }
  };

  const handleResend = async () => {
    try {
      await api.post("/auth/resend-otp", { email });
      toast.success("New OTP sent to email");

      setTimer(60);
      setCanResend(false);
    } catch (err) {
      toast.error(err.response?.data || "Failed to resend OTP");
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="logo">Verify Email</h1>

        <p className="subtitle">Enter the OTP sent to your email</p>

        <form onSubmit={handleVerify}>
          <input
            type="text"
            placeholder="Enter OTP"
            value={otp}
            onChange={e => setOtp(e.target.value)}
            required
          />

          <button type="submit" disabled={loading}>
            {loading ? <div className="spinner"></div> : "Verify OTP"}
          </button>
        </form>

        <div className="divider"></div>

        {canResend ? (
          <button className="register-btn" onClick={handleResend}>
            Resend OTP
          </button>
        ) : (
          <p className="subtitle">Resend OTP in {timer}s</p>
        )}
      </div>
    </div>
  );
}

export default VerifyOtp;
