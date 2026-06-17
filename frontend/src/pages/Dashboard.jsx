import React, { useState, useEffect } from 'react';
import { doctorService, patientService, appointmentService, departmentService } from '../services/api';
import { 
  UserSquare2, 
  Users, 
  CalendarRange, 
  Building2,
  TrendingUp,
  Activity,
  HeartPulse
} from 'lucide-react';

const Dashboard = () => {
  const [stats, setStats] = useState({
    doctors: 0,
    patients: 0,
    appointments: 0,
    departments: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [docs, pats, apps, deps] = await Promise.all([
          doctorService.getAll().catch(() => ({ data: [] })),
          patientService.getAll().catch(() => ({ data: [] })),
          appointmentService.getAll().catch(() => ({ data: [] })),
          departmentService.getAll().catch(() => ({ data: [] })),
        ]);

        setStats({
          doctors: docs.data?.length || 0,
          patients: pats.data?.length || 0,
          appointments: apps.data?.length || 0,
          departments: deps.data?.length || 0,
        });
      } catch (err) {
        console.error("Failed to fetch stats", err);
        setError("Unable to connect to backend service");
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  const statCards = [
    { name: 'Total Doctors', value: stats.doctors, icon: UserSquare2, color: '#4F46E5', bg: 'rgba(79, 70, 229, 0.1)' },
    { name: 'Total Patients', value: stats.patients, icon: Users, color: '#10B981', bg: 'rgba(16, 185, 129, 0.1)' },
    { name: 'Appointments Scheduled', value: stats.appointments, icon: CalendarRange, color: '#F59E0B', bg: 'rgba(245, 158, 11, 0.1)' },
    { name: 'Departments', value: stats.departments, icon: Building2, color: '#EC4899', bg: 'rgba(236, 72, 153, 0.1)' },
  ];

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ marginBottom: '0.25rem' }}>Hospital Dashboard</h1>
          <p style={{ margin: 0 }}>Welcome to HopeCare Management System. Here is today's overview.</p>
        </div>
        <div className="badge badge-success flex items-center gap-4" style={{ padding: '0.5rem 1rem' }}>
          <TrendingUp size={16} />
          <span>System active</span>
        </div>
      </div>

      {error && (
        <div style={{ padding: '1rem', backgroundColor: '#FEF2F2', border: '1px solid #FCA5A5', color: '#B91C1C', borderRadius: '0.5rem', marginBottom: '1.5rem' }}>
          <strong>Notice:</strong> {error}. Using simulated/fallback stats. Please ensure your backend Spring Boot application is running on port 8080.
        </div>
      )}

      <div className="grid grid-cols-3" style={{ gap: '1.5rem', gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))', marginBottom: '2rem' }}>
        {statCards.map((card, index) => {
          const Icon = card.icon;
          return (
            <div key={index} className="glass-card flex items-center justify-between">
              <div>
                <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 500 }}>{card.name}</span>
                <h2 style={{ fontSize: '2rem', margin: '0.5rem 0 0 0', fontWeight: '700' }}>
                  {loading ? '...' : card.value}
                </h2>
              </div>
              <div style={{ backgroundColor: card.bg, padding: '1rem', borderRadius: '1rem', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Icon size={28} style={{ color: card.color }} />
              </div>
            </div>
          );
        })}
      </div>

      <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))', gap: '1.5rem' }}>
        <div className="glass-card">
          <div className="flex items-center gap-4 mb-4">
            <HeartPulse className="text-primary-color" style={{ color: 'var(--primary-color)' }} size={24} />
            <h3 style={{ margin: 0 }}>Quick Diagnostics & Care</h3>
          </div>
          <p>
            Manage electronic medical records, analyze patient medical histories, assign specialist doctors, and control standard appointments.
          </p>
          <p>
            Seamless coordination of healthcare professionals across modern digital systems leads to better clinical results.
          </p>
        </div>

        <div className="glass-card" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
          <h3 style={{ marginBottom: '1rem' }}>Today's Healthcare Tip</h3>
          <div style={{ borderLeft: '4px solid var(--secondary-color)', paddingLeft: '1rem', fontStyle: 'italic', color: 'var(--text-secondary)' }}>
            "Providing patient-centered, compassionate care is the cornerstone of high-quality hospital services. Connect with patients actively to ensure great outcomes."
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
