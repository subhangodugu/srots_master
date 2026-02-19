import React, { useEffect, useState } from 'react';
import { Briefcase, Users, Award, Building, Activity } from 'lucide-react';
import { AnalyticsService } from '@/services/analyticsService';

// Sub-components
import { AnalyticsStatsGrid } from './analytics/AnalyticsStatsGrid';
import { AnalyticsChartsRow } from './analytics/AnalyticsChartsRow';
import { AnalyticsRecentJobs } from './analytics/AnalyticsRecentJobs';
import { AnalyticsJobTypePie } from './analytics/AnalyticsJobTypePie';

export const AnalyticsDashboard: React.FC = () => {
    const [data, setData] = useState<any>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        AnalyticsService.getOverview()
            .then((res: any) => {
                setData(res);
                setLoading(false);
            })
            .catch(() => {
                setLoading(false);
            });
    }, []);

    if (loading) return <div className="p-8 text-center text-gray-500">Loading analytics...</div>;
    if (!data) return <div className="p-8 text-center text-red-500">Failed to load analytics data.</div>;

    const stats = data.stats || { placedStudents: 0, totalStudents: 0, activeJobs: 0, companiesVisited: 0 };

    const statItems = [
        { label: 'Placed Students', value: (stats.placedStudents || 0).toString(), change: 'Live', icon: Users, color: 'text-blue-600', bg: 'bg-blue-50' },
        { label: 'Total Students', value: (stats.totalStudents || 0).toString(), change: 'Total', icon: Briefcase, color: 'text-purple-600', bg: 'bg-purple-50' },
        { label: 'Active Jobs', value: (data.recentJobs?.length || 0).toString(), change: 'Open', icon: Award, color: 'text-green-600', bg: 'bg-green-50' },
        { label: 'Participating Companies', value: (stats.companiesVisited || 0).toString(), change: 'Active', icon: Building, color: 'text-orange-600', bg: 'bg-orange-50' }
    ];

    const COLORS = ['#2563eb', '#7c3aed', '#db2777', '#ea580c', '#10b981'];

    return (
        <div className="space-y-6 animate-in fade-in slide-in-from-bottom-4">
            <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
                <div>
                    <h2 className="text-2xl font-bold text-gray-800">Placement Analytics</h2>
                    <p className="text-sm text-gray-500">Real-time insights into campus recruitment performance.</p>
                </div>
                <div className="flex gap-2">
                    <span className="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-bold flex items-center gap-1">
                        <Activity size={14} /> Live Data
                    </span>
                </div>
            </div>

            {/* Stats Grid */}
            <AnalyticsStatsGrid stats={statItems} />

            {/* Charts Row 1 */}
            <AnalyticsChartsRow
                branchData={data.branchDistribution}
                progressData={data.placementProgress}
            />

            {/* Charts Row 2 */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                <AnalyticsRecentJobs jobs={data.recentJobs} />
                <AnalyticsJobTypePie data={data.jobTypes} colors={COLORS} />
            </div>
        </div>
    );
};
