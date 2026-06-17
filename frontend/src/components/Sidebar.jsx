import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  LayoutDashboard, 
  UserSquare2, 
  Users, 
  CalendarRange, 
  Building2,
  Activity
} from 'lucide-react';

const Sidebar = () => {
  const menuItems = [
    { path: '/', name: 'Dashboard', icon: LayoutDashboard },
    { path: '/doctors', name: 'Doctors', icon: UserSquare2 },
    { path: '/patients', name: 'Patients', icon: Users },
    { path: '/appointments', name: 'Appointments', icon: CalendarRange },
    { path: '/departments', name: 'Departments', icon: Building2 },
  ];

  return (
    <aside className="sidebar">
      <div className="flex items-center gap-4 mb-6">
        <Activity className="text-primary-color" style={{ color: 'var(--primary-color)' }} size={32} />
        <h2 style={{ margin: 0, fontSize: '1.25rem' }}>HopeCare</h2>
      </div>
      <nav style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
        {menuItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.path}
              to={item.path}
              style={({ isActive }) => ({
                display: 'flex',
                alignItems: 'center',
                gap: '0.75rem',
                padding: '0.75rem 1rem',
                borderRadius: '0.5rem',
                color: isActive ? 'var(--primary-color)' : 'var(--text-secondary)',
                backgroundColor: isActive ? 'rgba(79, 70, 229, 0.1)' : 'transparent',
                textDecoration: 'none',
                fontWeight: isActive ? '600' : '500',
                transition: 'all 0.2s ease',
              })}
            >
              <Icon size={20} />
              <span>{item.name}</span>
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
};

export default Sidebar;
