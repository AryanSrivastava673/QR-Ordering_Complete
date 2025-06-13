import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useTableContext } from './TableContext';

const TableRouterContextUpdater = () => {
    const { setTableNo } = useTableContext();
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const tableNoFromPath = location.pathname.split('/')[1]; // Assuming URL is like /T1
        if (tableNoFromPath) {
            setTableNo(tableNoFromPath);
        } else {
            navigate('/T2', { replace: true });  // Default navigation if no tableNo in path
        }
    }, [location, navigate, setTableNo]);

    return null; // Component does not render anything
};

export default TableRouterContextUpdater;