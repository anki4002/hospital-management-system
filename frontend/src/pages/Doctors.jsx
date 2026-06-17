import React, { useState, useEffect } from 'react';
import { doctorService, departmentService } from '../services/api';
import { Plus, Trash2, Calendar, Stethoscope } from 'lucide-react';

const Doctors = () => {
  const [doctors, setDoctors] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Form State
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    doctorName: '',
    specialization: '',
    departmentId: '',
    availableDays: []
  });

  const daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [docsResponse, depsResponse] = await Promise.all([
        doctorService.getAll(),
        departmentService.getAll()
      ]);
      setDoctors(docsResponse.data || []);
      setDepartments(depsResponse.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch doctors data. Make sure backend is running.");
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleDayToggle = (day) => {
    const currentDays = [...formData.availableDays];
    const index = currentDays.indexOf(day);
    if (index > -1) {
      currentDays.splice(index, 1);
    } else {
      currentDays.push(day);
    }
    setFormData({ ...formData, availableDays: currentDays });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.doctorName || !formData.specialization || !formData.departmentId) {
      alert("Please fill in all required fields.");
      return;
    }

    try {
      const payload = {
        doctorName: formData.doctorName,
        specialization: formData.specialization,
        department: {
          departmentId: parseInt(formData.departmentId)
        },
        availableDays: formData.availableDays
      };

      await doctorService.create(payload);
      setShowForm(false);
      setFormData({ doctorName: '', specialization: '', departmentId: '', availableDays: [] });
      fetchData();
    } catch (err) {
      console.error(err);
      alert("Failed to add doctor.");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this doctor?")) {
      try {
        await doctorService.delete(id);
        fetchData();
      } catch (err) {
        console.error(err);
        alert("Failed to delete doctor.");
      }
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ marginBottom: '0.25rem' }}>Doctors Directory</h1>
          <p style={{ margin: 0 }}>Manage specialized doctors and their weekly availability.</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          <Plus size={18} />
          {showForm ? 'View List' : 'Add Doctor'}
        </button>
      </div>

      {error && (
        <div style={{ padding: '1rem', backgroundColor: '#FEF2F2', border: '1px solid #FCA5A5', color: '#B91C1C', borderRadius: '0.5rem', marginBottom: '1.5rem' }}>
          {error}
        </div>
      )}

      {showForm ? (
        <div className="glass-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Add New Doctor</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label className="form-label">Doctor Name *</label>
              <input
                type="text"
                name="doctorName"
                className="form-control"
                placeholder="Dr. Jane Smith"
                value={formData.doctorName}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Specialization *</label>
              <input
                type="text"
                name="specialization"
                className="form-control"
                placeholder="Cardiology / Pediatrics"
                value={formData.specialization}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Department *</label>
              <select
                name="departmentId"
                className="form-control"
                value={formData.departmentId}
                onChange={handleInputChange}
                required
              >
                <option value="">Select a department</option>
                {departments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Available Days</label>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(110px, 1fr))', gap: '0.5rem', marginTop: '0.5rem' }}>
                {daysOfWeek.map((day) => {
                  const isSelected = formData.availableDays.includes(day);
                  return (
                    <button
                      type="button"
                      key={day}
                      onClick={() => handleDayToggle(day)}
                      style={{
                        padding: '0.5rem',
                        fontSize: '0.8rem',
                        border: isSelected ? '1px solid var(--primary-color)' : '1px solid var(--border-color)',
                        backgroundColor: isSelected ? 'rgba(79, 70, 229, 0.1)' : 'white',
                        color: isSelected ? 'var(--primary-color)' : 'var(--text-secondary)',
                        borderRadius: '0.5rem',
                        cursor: 'pointer',
                        fontWeight: isSelected ? '600' : 'normal',
                      }}
                    >
                      {day}
                    </button>
                  );
                })}
              </div>
            </div>

            <button type="submit" className="btn btn-primary w-full" style={{ marginTop: '1rem' }}>
              Submit Registration
            </button>
          </form>
        </div>
      ) : (
        <div className="table-container">
          {loading ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading doctors...</div>
          ) : doctors.length === 0 ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>No doctors found. Try adding one!</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Specialization</th>
                  <th>Department</th>
                  <th>Available Days</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {doctors.map((doc) => (
                  <tr key={doc.doctorId}>
                    <td>{doc.doctorId}</td>
                    <td style={{ fontWeight: '500' }}>
                      <div className="flex items-center gap-4">
                        <Stethoscope size={16} className="text-primary-color" style={{ color: 'var(--primary-color)' }} />
                        <span>{doc.doctorName}</span>
                      </div>
                    </td>
                    <td>{doc.specialization}</td>
                    <td>
                      <span className="badge badge-info">{doc.department?.departmentName || 'N/A'}</span>
                    </td>
                    <td>
                      <div className="flex" style={{ gap: '0.25rem', flexWrap: 'wrap' }}>
                        {doc.availableDays && doc.availableDays.length > 0 ? (
                          doc.availableDays.map((day) => (
                            <span key={day} className="badge badge-success" style={{ fontSize: '0.65rem' }}>
                              {day.substring(0, 3)}
                            </span>
                          ))
                        ) : (
                          <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)' }}>None</span>
                        )}
                      </div>
                    </td>
                    <td>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(doc.doctorId)}>
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

export default Doctors;
