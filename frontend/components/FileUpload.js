// components/FileUpload.js
import React, { useState } from 'react';

const FileUpload = ({ handleFileUpload }) => {
  const [selectedFile, setSelectedFile] = useState(null);

  const onFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const onSubmit = () => {
    if (selectedFile) {
      handleFileUpload(selectedFile);
    }
  };

  return (
    <div>
      <input type="file" onChange={onFileChange} />
      {selectedFile && (
        <button onClick={onSubmit}>Process</button>
      )}
    </div>
  );
};

export default FileUpload;
