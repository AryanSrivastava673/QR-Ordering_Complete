// import React, { createContext, useState, useContext } from 'react';

// const TableContext = createContext();

// export const useTableContext = () => useContext(TableContext);

// export const TableProvider = ({ children }) => {
//     const [tableNo, setTableNo] = useState('T2'); // default table number, can be set dynamically

//     return (
//         <TableContext.Provider value={{ tableNo, setTableNo }}>
//             {children}
//         </TableContext.Provider>
//     );
// };


import React, { createContext, useState, useContext } from 'react';

const TableContext = createContext();

export const useTableContext = () => useContext(TableContext);

export const TableProvider = ({ children }) => {
    const [tableNo, setTableNo] = useState('T2'); // default table number

    return (
        <TableContext.Provider value={{ tableNo, setTableNo }}>
            {children}
        </TableContext.Provider>
    );
};