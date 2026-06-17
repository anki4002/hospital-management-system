import React, { useState, useEffect } from 'react';
import { patientService } from '../services/api';
import { Plus, Trash2, User } from 'lucide-react';

const Patients = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Form State
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    patientName: '',
    age: '',
    gender: 'MALE',
    phone: '',
    email: ''
  });

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await patientService.getAll();
      setPatients(response.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch patients data.");
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.patientName || !formData.age || !formData.phone || !formData.email) {
      alert("Please fill in all required fields.");
      return;
    }

    try {
      const payload = {
        patientName: formData.patientName,
        age: parseInt(formData.age),
        gender: formData.gender,
        phone: formData.phone,
        email: formData.email
      };

      await patientService.create(payload);
      setShowForm(false);
      setFormData({ patientName: '', age: '', gender: 'MALE', phone: '', email: '' });
      fetchPatients();
    } catch (err) {
      console.error(err);
      alert("Failed to add patient. Ensure email or phone is unique.");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this patient record?")) {
      try {
        await patientService.delete(id);
        fetchPatients();
      } catch (err) {
        console.error(err);
        alert("Failed to delete patient.");
      }
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ marginBottom: '0.25rem' }}>Patients Directory</h1>
          <p style={{ margin: 0 }}>Add patients, access metrics, and monitor records.</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          <Plus size={18} />
          {showForm ? 'View List' : 'Add Patient'}
        </button>
      </div>

      {error && (
        <div style={{ padding: '1rem', backgroundColor: '#FEF2F2', border: '1px solid #FCA5A5', color: '#B91C1C', borderRadius: '0.5rem', marginBottom: '1.5rem' }}>
          {error}
        </div>
      )}

      {showForm ? (
        <div className="glass-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Register Patient</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label className="form-label">Full Name *</label>
              <input
                type="text"
                name="patientName"
                className="form-control"
                placeholder="John Doe"
                value={formData.patientName}
                onChange={handleInputChange}
                required
              />
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
              <div className="form-group">
                <label className="form-label">Age *</label>
                <input
                  type="number"
                  name="age"
                  className="form-control"
                  placeholder="30"
                  value={formData.age}
                  onChange={handleInputChange}
                  required
                />
              </div>

              <div className="form-group">
                <label className="form-label">Gender</label>
                <select
                  name="gender"
                  className="form-control"
                  value={formData.gender}
                  onChange={handleInputChange}
                >
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="OTHER">Other</option>
                </select>
              </div>
            </div>

            <div className="form-group">
              <label className="form-label">Phone Number *</label>
              <input
                type="tel"
                name="phone"
                className="form-control"
                placeholder="+1 234 567 890"
                value={formData.phone}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Email Address *</label>
              <input
                type="email"
                name="email"
                className="form-control"
                placeholder="john.doe@example.com"
                value={formData.email}
                onChange={handleInputChange}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-full" style={{ marginTop: '1rem' }}>
              Register Patient
            </button>
          </form>
        </div>
      ) : (
        <div className="table-container">
          {loading ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading patients...</div>
          ) : patients.length === 0 ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>No patients found. Try adding one!</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Age</th>
                  <th>Gender</th>
                  <th>Phone</th>
                  <th>Email</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {patients.map((pat) => (
                  <tr key={pat.patientId}>
                    <td>{pat.patientId}</td>
                    <td style={{ fontWeight: '500' }}>
                      <div className="flex items-center gap-4">
                        <User size={16} className="text-primary-color" style={{ color: 'var(--primary-color)' }} />
                        <span>{pat.patientName}</span>
                      </div>
                    </td>
                    <td>{pat.age}</td>
                    <td>
                      <span className={`badge ${pat.gender === 'MALE' ? 'badge-info' : 'badge-success'}`}>
                        {pat.gender}
                      </span>
                    </td>
                    <td>{pat.phone}</td>
                    <td>{pat.email}</td>
                    <td>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(pat.patientId)}>
                        <Trash2 size={16} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
};

export default Patients;
