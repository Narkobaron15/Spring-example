import React from 'react';

import uploadIcon from '../../../assets/upload.svg';
import './dropzone.css';

type DropzoneArgs = {
    fileOnChange: (event: React.ChangeEvent<HTMLInputElement>) => void,
}

export default function Dropzone({ fileOnChange }: DropzoneArgs) {
    return (
        <div className="dropzone">
            <label htmlFor="dropzone-file" className="">
                <div className="">
                    <img className="" src={uploadIcon} alt="Drag & drop icon" />
                    <p className="text-1">
                        <span className="font-semibold">Click to upload</span>
                        or drag and drop
                    </p>
                    <p className="text-2">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                </div>
                <input id="dropzone-file" type="file" className="hidden" onChange={fileOnChange} />
            </label>
        </div>
    );
}