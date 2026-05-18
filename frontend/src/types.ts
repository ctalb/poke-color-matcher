export interface DmcFloss {
    number: string;
    name: string;
    hex: string;
}

export interface FlossColorMatch {
    extractedColor: number[];
    match: DmcFloss;
}

export interface MatchResult {
    name: string;
    imagePath: string;
    matches: FlossColorMatch[];
}