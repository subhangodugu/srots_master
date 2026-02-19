import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api/v1';

export const AnalyticsService = {
    async getOverview() {
        try {
            const res = await axios.get(`${API_BASE_URL}/analytics/overview`);
            return res.data;
        } catch (error: any) {
            console.error('Error fetching analytics overview:', error.response?.data || error.message);
            throw error;
        }
    }
};
