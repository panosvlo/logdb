import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

function Register() {
  const [user, setUser] = useState({
    name: '',
    username: '',
    password: '',
    address: '',
    email: ''
  });
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const handleRegister = async (event) => {
    event.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/users/register', user);
      setSuccessMessage('Registration successful! Redirecting to login...');
      setTimeout(() => {
        navigate('/login'); // Redirect to login page after message disappears
      }, 2000); // Wait 3 seconds before redirecting
    } catch (error) {
      console.error('Registration failed:', error);
      setErrorMessage('Registration failed. Please try again with a different username.');
    }
  };

  return (
      <div className="auth-form">
        <h2>Register</h2>
        {successMessage && <p className="message">{successMessage}</p>}
        {errorMessage && <p className="error">{errorMessage}</p>} {/* Use the 'error' class for error messages */}
        <form onSubmit={handleRegister}>
          <input
            type="text"
            name="name"
            placeholder="Full Name"
            value={user.name}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="username"
            placeholder="Username"
            value={user.username}
            onChange={handleInputChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={user.password}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="address"
            placeholder="Address"
            value={user.address}
            onChange={handleInputChange}
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={user.email}
            onChange={handleInputChange}
            required
          />
          <button type="submit">Register</button>
        </form>
        <p>
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>
    );
  }

  export default Register;
