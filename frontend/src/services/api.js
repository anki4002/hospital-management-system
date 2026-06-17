const API_BASE_URL = 'http://localhost:8080/hospital';

export const api = {
    async get(endpoint) {
        const response = await fetch(`${API_BASE_URL}${endpoint}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    },

    async post(endpoint, data) {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    },

    async put(endpoint, data) {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    },

    async delete(endpoint) {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    }
};

export const doctorService = {
    getAll: () => api.get('/doctor/get'),
    getById: (id) => api.get(`/doctor/get/${id}`),
    create: (data) => api.post('/doctor/add', data),
    update: (data) => api.put('/doctor/update', data),
    delete: (id) => api.delete(`/doctor/delete/${id}`)
};

export const patientService = {
    getAll: () => api.get('/patient/get'),
    getById: (id) => api.get(`/patient/get/${id}`),
    create: (data) => api.post('/patient/add', data),
    update: (data) => api.put('/patient/update', data),
    delete: (id) => api.delete(`/patient/delete/${id}`)
};

export const appointmentService = {
    getAll: () => api.get('/appointment/get'),
    getById: (id) => api.get(`/appointment/get/${id}`),
    create: (data) => api.post('/appointment/add', data),
    update: (data) => api.put('/appointment/update', data),
    delete: (id) => api.delete(`/appointment/delete/${id}`)
};

export const departmentService = {
    getAll: () => api.get('/department/get'),
    getById: (id) => api.get(`/department/get/${id}`),
    create: (data) => api.post('/department/add', data),
    update: (data) => api.put('/department/update', data),
    delete: (id) => api.delete(`/department/delete/${id}`)
};
