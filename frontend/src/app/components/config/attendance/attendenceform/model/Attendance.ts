export class Attendance {
    id?: number;
    checkinTime?: string; // Use Date or string as per your preference
    checkoutTime?: string; // Use Date or string as per your preference   
    remarks?: string;

    constructor() {
        this.checkinTime = '';
        this.checkoutTime = '';
        this.remarks = '';
    }
}


