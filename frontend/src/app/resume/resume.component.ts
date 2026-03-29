import { Component, OnInit } from '@angular/core';
import { ResumeService } from '../shared/services/resume.service';
@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css']
})
export class ResumeComponent implements OnInit {
  selectedFile: File | null = null;
  loading      = false;
  uploading    = false;
  error        = '';
  success      = '';
  currentResume: any = null;
  dragOver     = false;

  constructor(private resumeService: ResumeService) {}

  ngOnInit(): void {
    this.loading = true;
    this.resumeService.getMyResume().subscribe({
      next: (res: any) => {
        this.loading = false;
        if (res.status === 'success') this.currentResume = res;
      },
      error: () => { this.loading = false; }
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
      this.error = '';
    } else {
      this.error = 'Please select a PDF file only.';
    }
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.dragOver = false;
    const file = event.dataTransfer?.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
      this.error = '';
    } else {
      this.error = 'Please drop a PDF file only.';
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.dragOver = true;
  }

  onDragLeave(): void { this.dragOver = false; }

  upload(): void {
    if (!this.selectedFile) { this.error = 'Please select a PDF file first.'; return; }
    this.uploading = true;
    this.error     = '';
    this.success   = '';

    this.resumeService.uploadResume(this.selectedFile).subscribe({
      next: (res: any) => {
        this.uploading = false;
        if (res.status === 'success') {
          this.success       = 'Resume uploaded successfully!';
          this.currentResume = res;
          this.selectedFile  = null;
        } else {
          this.error = res.message;
        }
      },
      error: () => {
        this.uploading = false;
        this.error     = 'Upload failed. Please try again.';
      }
    });
  }
}
