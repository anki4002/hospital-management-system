import React, { useState, useEffect } from 'react';
import { appointmentService, doctorService, patientService } from '../services/api';
import { Plus, Trash2, CalendarDays, CheckCircle2, XCircle } from 'lucide-react';

const Appointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Form State
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    patientId: '',
    doctorId: '',
    appointmentDateTime: '',
    status: 'BOOKED'
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [appsResponse, docsResponse, patsResponse] = await Promise.all([
        appointmentService.getAll(),
        doctorService.getAll(),
        patientService.getAll()
      ]);
      setAppointments(appsResponse.data || []);
      setDoctors(docsResponse.data || []);
      setPatients(patsResponse.data || []);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch appointments. Make sure database and backend are available.");
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
    if (!formData.patientId || !formData.doctorId || !formData.appointmentDateTime) {
      alert("Please complete the booking form.");
      return;
    }

    try {
      const payload = {
        appointmentDateTime: formData.appointmentDateTime,
        status: formData.status,
        doctor: { doctorId: parseInt(formData.doctorId) },
        patient: { patientId: parseInt(formData.patientId) }
      };

      await appointmentService.create(payload);
      setShowForm(false);
      setFormData({ patientId: '', doctorId: '', appointmentDateTime: '', status: 'BOOKED' });
      fetchData();
    } catch (err) {
      console.error(err);
      alert("Failed to schedule appointment. A doctor may not be free at this time or has a scheduling conflict.");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to cancel/remove this appointment?")) {
      try {
        await appointmentService.delete(id);
        fetchData();
      } catch (err) {
        console.error(err);
        alert("Failed to delete appointment.");
      }
    }
  };

  const getStatusBadge = (status) => {
    switch (status) {
      case 'BOOKED': return <span className="badge badge-info">Booked</span>;
      case 'COMPLETED': return <span className="badge badge-success">Completed</span>;
      case 'CANCELLED': return <span className="badge badge-danger" style={{ backgroundColor: '#FEE2E2', color: '#991B1B' }}>Cancelled</span>;
      default: return <span className="badge">{status}</span>;
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ marginBottom: '0.25rem' }}>Appointments</h1>
          <p style={{ margin: 0 }}>Schedule consulting slots and track clinical checkups.</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          <Plus size={18} />
          {showForm ? 'View List' : 'Schedule Appointment'}
        </button>
      </div>

      {error && (
        <div style={{ padding: '1rem', backgroundColor: '#FEF2F2', border: '1px solid #FCA5A5', color: '#B91C1C', borderRadius: '0.5rem', marginBottom: '1.5rem' }}>
          {error}
        </div>
      )}

      {showForm ? (
        <div className="glass-card" style={{ maxWidth: '600px', margin: '0 auto' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Book New Appointment</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label className="form-label">Patient *</label>
              <select
                name="patientId"
                className="form-control"
                value={formData.patientId}
                onChange={handleInputChange}
                required
              >
                <option value="">Select Patient</option>
                {patients.map((pat) => (
                  <option key={pat.patientId} value={pat.patientId}>
                    {pat.patientName} (ID: {pat.patientId})
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Doctor *</label>
              <select
                name="doctorId"
                className="form-control"
                value={formData.doctorId}
                onChange={handleInputChange}
                required
              >
                <option value="">Select Doctor</option>
                {doctors.map((doc) => (
                  <option key={doc.doctorId} value={doc.doctorId}>
                    {doc.doctorName} - {doc.specialization}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Appointment Date & Time *</label>
              <input
                type="datetime-local"
                name="appointmentDateTime"
                className="form-control"
                value={formData.appointmentDateTime}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Status</label>
              <select
                name="status"
                className="form-control"
                value={formData.status}
                onChange={handleInputChange}
              >
                <option value="BOOKED">Booked</option>
                <option value="COMPLETED">Completed</option>
                <option value="CANCELLED">Cancelled</option>
              </select>
            </div>

            <button type="submit" className="btn btn-primary w-full" style={{ marginTop: '1rem' }}>
              Confirm Booking
            </button>
          </form>
        </div>
      ) : (
        <div className="table-container">
          {loading ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading appointments...</div>
          ) : appointments.length === 0 ? (
            <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>No appointments scheduled.</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Patient</th>
                  <th>Doctor</th>
                  <th>Specialization</th>
                  <th>Date & Time</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {appointments.map((app) => (
                  <tr key={app.appointmentId}>
                    <td>{app.appointmentId}</td>
                    <td style={{ fontWeight: '500' }}>{app.patient?.patientName || 'N/A'}</td>
                    <td>{app.doctor?.doctorName || 'N/A'}</td>
                    <td>{app.doctor?.specialization || 'N/A'}</td>
                    <td>
                      <div className="flex items-center gap-4">
                        <CalendarDays size={16} style={{ color: 'var(--text-secondary)' }} />
                        <span>{new Date(app.appointmentDateTime).toLocaleString()}</span>
                      </div>
                    </td>
                    <td>{getStatusBadge(app.status)}</td>
                    <td>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(app.appointmentId)}>
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

export default Appointments;
