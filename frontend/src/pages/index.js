import React, { useState } from 'react';

import EmployeeTable from '../../components/EmployeeTable';
import FileUpload from '../../components/FileUpload';
import JobSummaryTable from '../../components/JobSummaryTable';

const Home = () => {
  const [employees, setEmployees] = useState([]);
  const [jobSummaries, setJobSummaries] = useState([]);

  const handleFileUpload = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    console.log(formData)
    try {
      const uploadResponse = await fetch('http://localhost:8080/api/upload', {
        method: 'POST',
        body: formData,
      });

      if (uploadResponse.ok) {
        const dataResponse = await fetch('http://localhost:8080/api/average-salaries');
        if (dataResponse.ok) {
          const data = await dataResponse.json();
          setEmployees(data.employees);
          setJobSummaries(data.jobSummaries);
        } else {
          console.error('Failed to fetch processed data');
        }
      } else {
        console.error('Failed to upload file');
      }
    } catch (error) {
      console.error('Error uploading file:', error);
    }
  };

  return (
    <div>
      <h1>Upload File</h1>
      <FileUpload handleFileUpload={handleFileUpload} />

      <h2>Employee Information</h2>
      <EmployeeTable employees={employees} />

      <h2>Job Summary</h2>
      <JobSummaryTable jobSummaries={jobSummaries} />
    </div>
  );
};

export default Home;
