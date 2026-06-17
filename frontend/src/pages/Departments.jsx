import React, { useState, useEffect } from 'react';
import { departmentService } from '../services/api';
import { Plus, Trash2, Building2 } from 'lucide-react';

const Departments = () => {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Form State
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    departmentName: ''
  });

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await departmentService.getAll();
      setDepartments(response.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch departments. Make sure backend is running.");
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
    if (!formData.departmentName) {
      alert("Please fill in the department name.");
      return;
    }

    try {
      await departmentService.create({
        departmentName: formData.departmentName
      });
      setShowForm(false);
      setFormData({ departmentName: '' });
      fetchDepartments();
    } catch (err) {
      console.error(err);
      alert("Failed to create department. Make sure the name is unique.");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this department?")) {
      try {
        await departmentService.delete(id);
        fetchDepartments();
      } catch (err) {
        console.error(err);
        alert("Failed to delete department. Some doctors may still be assigned to it.");
      }
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ marginBottom: '0.25rem' }}>Hospital Departments</h1>
          <p style={{ margin: 0 }}>Create and organize clinical wards and general services.</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          <Plus size={18} />
          {showForm ? 'View List' : 'Add Department'}
        </button>
      </div>

      {error && (
        <div style={{ padding: '1rem', backgroundColor: '#FEF2F2', border: '1px solid #FCA5A5', color: '#B91C1C', borderRadius: '0.5rem', marginBottom: '1.5rem' }}>
          {error}
        </div>
      )}

      {showForm ? (
        <div className="glass-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Create Department</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label className="form-label">Department Name *</label>
              <input
                type="text"
                name="departmentName"
                className="form-control"
                placeholder="Cardiology / Neurology"
                value={formData.departmentName}
                onChange={handleInputChange}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-full" style={{ marginTop: '1rem' }}>
              Create Department
            </button>
          </form>
        </div>
      ) : (
        <div className="table-container">
          {loading ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading departments...</div>
          ) : departments.length === 0 ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>No departments configured. Please add one.</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Department Name</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {departments.map((dept) => (
                  <tr key={dept.departmentId}>
                    <td>{dept.departmentId}</td>
                    <td style={{ fontWeight: '500' }}>
                      <div className="flex items-center gap-4">
                        <Building2 size={16} className="text-primary-color" style={{ color: 'var(--primary-color)' }} />
                        <span>{dept.departmentName}</span>
                      </div>
                    </td>
                    <td>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(dept.departmentId)}>
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

export default Departments;
