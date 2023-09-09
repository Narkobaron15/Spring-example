import React from 'react';

import uploadIcon from '../../../assets/upload.svg';
import './dropzone.css';

type DropzoneArgs = {
    fileOnChange: (event: React.ChangeEvent<HTMLInputElement>) => void,
    id: string,
    name: string,
    multiple: boolean,
}

export default function Dropzone({ fileOnChange, id, name, multiple }: DropzoneArgs) {
    const inputid = id + "-dropzone-file";
    
    return (
        <div className="dropzone">
            <label htmlFor={inputid}>
                <div>
                    <img src={uploadIcon} alt="Drag & drop icon" />
                    <p className="text-1">
                        <span className="font-semibold">Click to upload</span>
                        or drag and drop
                    </p>
                    <p className="text-2">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                </div>
                <input id={inputid} name={name} multiple={multiple} type="file" className="hidden" onChange={fileOnChange} />
            </label>
        </div>
    );
}