interface DmcFloss {
    number: string;
    name: string;
    hex: string;
}

interface FlossColorMatch {
    extractedColor: number[];
    match: DmcFloss;
}

export interface MatchResult {
    name: string;
    imagePath: string;
    matches: FlossColorMatch[];
}